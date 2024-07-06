package com.qiqijin.todoList.model;

public enum Status {
    TODO,
    DONE,
    DELETE;

    public Status toggle() {
	if (this == Status.TODO) {
	    return Status.DONE;
	} else if (this == Status.DONE) {
	    return Status.TODO;
	} else {
	    return this;
	}
    }
}
