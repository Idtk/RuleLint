package com.idtk.mylink

import java.io.Serializable

/**
 * Time:2020/8/26
 * Author:Idtk
 * Desc:
 */

data class AData(var b:BData ? = BData()):Serializable{
    data class BData(var c:CData ?= CData()):Serializable{
        data class CData(var d:DData ?= DData()):Serializable{
        }
    }
}

data class DData(var name:Int ?= 0):Serializable
