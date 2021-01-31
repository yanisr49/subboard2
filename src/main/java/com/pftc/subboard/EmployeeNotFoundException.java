package com.pftc.subboard;

class EmployeeNotFoundException extends RuntimeException {

    /**
     * UID Généré
     */
	private static final long serialVersionUID = 1970636500985453412L;

EmployeeNotFoundException(Long id) {
    super("Could not find employee " + id);
  }
}