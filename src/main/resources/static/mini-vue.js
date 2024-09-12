class Compiler {
    

    
    constructor(viewModel) {
	// View Model is a Mini Vue instance.
	this.placeholderREG = /\{\{(.+?)\}\}/g
	this.viewModel = viewModel
	this.el = viewModel.$el
	this.compiler(this.el)
    }

    // Compile the template, process both text nodes and element nodes.
    compiler(el) {
	const childNodes = el.childNodes
	Array.from(childNodes).forEach(node => {
	    // process text node
	    if (this.isTextNode(node)) {
		this.compilerText(node)
	    } else if (this.isElementNode(node)) {
		// process element node
		this.compilerElement(node)
	    }
	    // Check for child nodes in the node. If present, recursively compile the node.
	    if (node.childNodes.length) {
		this.compiler(node)
	    }
	})
    }

    // Compile element node, process the directive
    compilerElement(node) {
	// Traverse all properties of the node.
	Array.from(node.attributes).forEach(attr => {
	    let attrName = attr.name
	    if (this.isDirective(attrName)) {
		// if the attribute is a directive, process the directive
		// because the directive is prefixed with "v-", we remove it for processing.
		attrName = attrName.substr(2)
		const key = attr.value
		this.update(node, key, attrName)
	    } else {
		this.updateAttr(node, attrName, attr.value)
	    }
	})
    }
    
    // for node attribute
    updateAttr(node, attrName, attrValue) {
	var keys = this._updateAttr(node, attrName, attrValue)
	var that = this
	for (var key of keys) {
	    new Watcher(this.viewModel, key, (newValue) => {
		that._updateAttr(node, attrName, newValue)
	    })
	}
    }
    
    _updateAttr(node, attrName, attrValue) {
	if (node.template == undefined) {
	    node.template = {}
	}
	if (node.template[attrName] == undefined) {
	    node.template[attrName] = attrValue
	}

	var value = node.template[attrName]
	var that = this
	var keys = []
	if (this.placeholderREG.test(value)) {
	    attrValue = value.replace(this.placeholderREG, (_, p1) => {
		// Update the view when data change.
		var key = p1.trim()
		keys.push(key)
		return that.viewModel[key]
	    })
	    node.setAttribute(attrName, attrValue);
	}
	return keys
    }

    // Execute the corresponding directive method.
    update(node, key, attrName) {
	let updateFn = this[attrName + 'Updater']
	// textUpdater or modelUpdater
	updateFn && updateFn.call(this, node, this.viewModel[key], key)
    }

    // for v-text directive
    textUpdater(node, value, key) {
	node.textContent = value

	// Update the view when data change.
	new Watcher(this.viewModel, key, (newValue) => {
	    node.textContent = newValue
	})
    }

    // for v-model directive
    modelUpdater(node, value, key) {
	node.value = value

	// Update the view when data change.
	new Watcher(this.viewModel, key, (newValue) => {
	    node.value = newValue
	})
	// Bidirectional binding
	node.addEventListener('input', () => {
	    this.viewModel[key] = node.value
	})
    }
    
    // for v-for directive
    forUpdater(node, array, key) {
	this._forUpdater(node, array, key);
	let that = this;
	// Update the view when data change.
	// Update the view only when the entire array changes, not when an individual item is updated.
	new Watcher(this.viewModel, key, (newValue) => {
	    that._forUpdater(node, newValue, key)
	})
	
    }
    
    _forUpdater(node, array, _) {
	if (node.template == undefined) {
	    node.template = {}
	}
	if (node.template.innerHTML == undefined) {
	    node.template.innerHTML = node.innerHTML
	}
	let innerHTML = node.template.innerHTML
	node.innerHTML = ""
	for (var value of array) {
	    var htmlItem = innerHTML.replaceAll(this.placeholderREG, (match, p1) => {
		if (typeof value === 'object') {
		    return value[p1.trim()] || match
		} else {
		    return value
		}});
	    node.innerHTML += htmlItem
	}
    }
        
    // Compile text nodes and handle interpolation expressions.
    compilerText(node) {
	const keys = this._compilerText(node)
	let that = this
	for (var key of keys) {
	    new Watcher(this.viewModel, key, (_) => {
		that._compilerText(node)
	    })
	}
    }
    
    _compilerText(node) {
	if (node.template == undefined) {
	    node.template = {}
	}
	if (node.template.textContent == undefined) {
	    node.template.textContent = node.textContent
	}
	let value = node.template.textContent
	let keys = []
	if (this.placeholderREG.test(value)) {
	    node.textContent = value.replace(this.placeholderREG, (_, p1) => {
		// Update the view when data change.
		var key = p1.trim()
		keys.push(key)
		return this.viewModel[key]
	    })
	}
	return keys
    }

    // Check if the attribute is a directive.
    isDirective(attrName) {
	return attrName.startsWith('v-')
    }

    // Check if the node is a text node.
    isTextNode(node) {
	return node.nodeType === 3
    }

    // Check if the node is a element node
    isElementNode(node) {
	return node.nodeType === 1
    }
}
class Dependency {
  constructor() {
    this.subs = []
  }

  addSub(sub) {
    if (sub && sub.update) {
      this.subs.push(sub)
    }
  }

  notify() {
    this.subs.forEach(sub => {
      sub.update()
    })
  }
}
class MiniVue {
    constructor(options) {
	// 1. Save options data
	this.$options = options || {}
	this.$data = options.data || {}
	this.$el = typeof options.el === 'string' ? document.querySelector(options.el) : options.el
	// 2. Inject data properties into the MiniVue instance.
	this._proxyData(this.$data)
	// 3. Observe data modification
	this.$observer = new Observer(this.$data)
	// 4. Compile the template and expressions.
	new Compiler(this)
    }
    _proxyData(data) {
	Object.keys(data).forEach(key => {
	    // The Object.defineProperty() static method defines a new property directly on an object,
	    // or modifies an existing property on an object, and returns the object.
	    Object.defineProperty(this, key, {
		enumerable: true,
		configurable: true,
		get() {
		    return data[key]
		},
		set(newValue) {
		    if (newValue === data[key]) {
			return
		    }
		    data[key] = newValue
		}
	    })
	})
    }
}
class Observer {
    constructor(data) {
	this.walk(data)
    }
    // Traverse the properties in the data and transform them into reactive data.
    walk(data) {
	if (!data || typeof data !== 'object') {
	    return
	}
	Object.keys(data).forEach((key) => {
	    this.defineReactive(data, key, data[key])
	})
    }
    // Define Reactive data
    defineReactive(obj, key, value) {
	const that = this
	// Gather dependencies and notify when data changes.
	let dependency = new Dependency()
	obj.dependency = dependency
	// Recursively traverse inner properties and convert them into reactive data.
	this.walk(value)
	Object.defineProperty(obj, key, {
	    enumerable: true,
	    configurable: true,
	    get() {
		// Gather dependencies.
		// the value of `Dependency.target` is a Watcher, you can see the value updates in the Watcher.js file.
		Dependency.target && dependency.addSub(Dependency.target)
		return value
	    },
	    set(newValue) {
		if (value === newValue) {
		    return
		}
		value = newValue
		// if the new value is an object, transform it into reactive data.
		that.walk(newValue)
		// send notification
		dependency.notify()
	    }
	})
    }
}
class Watcher {
    constructor(viewModel, key, callback) {
	this.viewModel = viewModel
	this.key = key

	// the callback function is responsible for updating the view.
	this.callback = callback
	// Set the watcher object as the static property `target' in the Dependency class.
	Dependency.target = this
	// Trigger the `get` method, which will call `addSub` within the `get` method.
	this.oldValue = viewModel[key]
	Dependency.target = null
    }

    // Refresh the view when data changes.
    update() {
	const newValue = this.viewModel[this.key]
	if (this.oldValue === newValue) {
	    return
	}
	this.oldValue = newValue
	// update the view
	this.callback(newValue)
    }

}
