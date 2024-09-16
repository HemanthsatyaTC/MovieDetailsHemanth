package com.example.moviedetails.utils

import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class ValidationUtilsTest{

    @Test
    fun `email with valid format returns true`() {
        assertTrue(isEmailValid("test@example.com"))
    }

    @Test
    fun `email without at symbol returns false`() {
        assertFalse(isEmailValid("testexample.com"))
    }

    @Test
    fun `password with sufficient length returns true`() {
        assertTrue(isPasswordValid("password123"))
    }

    @Test
    fun `password with insufficient length returns false`() {
        assertFalse(isPasswordValid("pass"))
    }
}