package com.tacme.news.helpers

import java.lang.Double.parseDouble

//
//  Validate.swift
//
//  Created by Ahmad on 8/26/18.
//  Copyright © 2018 Ahmad. All rights reserved.
//

object Validate{

    fun isValidEmail(email: String) : Boolean {
        val regEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}".toRegex()
        //return Pattern.matches(regEx, email)
        return regEx.matches(email)
    }

    fun isValidPhone(phone: String) : Boolean {
        //let emailRegEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}"
        //return NSPredicate(format:"SELF MATCHES %@", emailRegEx).evaluate(with: email)
        return true
    }

    fun isBlank(text: String) : Boolean {
        //trimming whitespaces and newlines
        return text.trimMargin().isBlank()
    }

    fun isValidPasswordLength(password: String, minPasswordLength:Int = 6) : Boolean {
        if( password.length < minPasswordLength ) {
            return false
        }
        return false
    }

    fun passwordsMatch(password: String, confirmPassword: String) : Boolean {
        if ( password == confirmPassword ) {
            return true
        }
        return false
    }

    fun isNumber(value: String) : Boolean {

        var numeric = true

        try {
            val num = parseDouble(value)
        } catch (e: NumberFormatException) {
            numeric = false
        }

        return numeric
    }

//    func matches(for regex: String, in text: String) -> [String] {
//
//        do {
//            let regex = try NSRegularExpression(pattern: regex)
//            let results = regex.matches(in: text,
//                                        range: NSRange(text.startIndex..., in: text))
//            return results.map {
//                String(text[Range($0.range, in: text)!])
//            }
//        } catch let error {
//            print("invalid regex: \(error.localizedDescription)")
//            return []
//        }
//    }
}