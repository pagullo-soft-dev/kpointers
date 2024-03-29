/*
LGPL v3.0
Copyright (C) 2019 Pedro Agullo Soliveres
p.agullo.soliveres@gmail.com

KPointers is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 3 of the License, or (at your option) any later version.

KPointers is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software Foundation,
Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

package com.softwarementors.kpointers

import com.softwarementors.kpointers.impl.unsafe

@kotlin.ExperimentalUnsignedTypes
val memAccess : MemAccess = MemAccess(unsafe)

@kotlin.ExperimentalUnsignedTypes
class MemAccess(private val unsafe : sun.misc.Unsafe) {
   fun get(address : BooleanPointer) : Boolean {
      val r = unsafe.getByte( address.toPointer().toUnsafePointer())
      return r != 0.toByte()
   }

   fun put(address : BooleanPointer, v : Boolean) : Unit {
      var bv = 0.toByte()
      if( v ) {
         bv = 1
      }
      unsafe.putByte( address.toPointer().toUnsafePointer(), bv)
   }

   fun get(address : BytePointer) : Byte {
      val r = unsafe.getByte( address.toPointer().toUnsafePointer())
      return r
   }

   fun put(address : BytePointer, v : Byte) : Unit {
      unsafe.putByte( address.toPointer().toUnsafePointer(), v)
   }

   fun get(address : UBytePointer) : UByte {
      val r = unsafe.getByte( address.toPointer().toUnsafePointer())
      return r.toUByte()
   }

   fun put(address : UBytePointer, v : UByte) : Unit {
      unsafe.putByte( address.toPointer().toUnsafePointer(), v.toByte())
   }

   fun get(address : CharPointer) : Char {
      val r = unsafe.getChar( address.toPointer().toUnsafePointer())
      return r
   }

   fun put(address : CharPointer, v : Char) : Unit {
      unsafe.putChar( address.toPointer().toUnsafePointer(), v)
   }

   fun get(address : ShortPointer) : Short {
      val r = unsafe.getShort( address.toPointer().toUnsafePointer())
      return r
   }

   fun put(address : ShortPointer, v : Short) : Unit {
      unsafe.putShort( address.toPointer().toUnsafePointer(), v)
   }

   fun get(address : UShortPointer) : UShort {
      val r = unsafe.getShort( address.toPointer().toUnsafePointer())
      return r.toUShort()
   }

   fun put(address : UShortPointer, v : UShort) : Unit {
      unsafe.putShort( address.toPointer().toUnsafePointer(), v.toShort())
   }
   
   fun get(address : IntPointer) : Int {
      val r = unsafe.getInt( address.toPointer().toUnsafePointer())
      return r
   }

   fun put(address : IntPointer, v : Int) : Unit {
      unsafe.putInt( address.toPointer().toUnsafePointer(), v)
   }
   
   fun get(address : UIntPointer) : UInt {
      val r = unsafe.getInt( address.toPointer().toUnsafePointer())
      return r.toUInt()
   }

   fun put(address : UIntPointer, v : UInt) : Unit {
      unsafe.putInt( address.toPointer().toUnsafePointer(), v.toInt())
   }

   fun get(address : LongPointer) : Long {
      val r = unsafe.getLong( address.toPointer().toUnsafePointer())
      return r
   }

   fun put(address : LongPointer, v : Long) : Unit {
      unsafe.putLong( address.toPointer().toUnsafePointer(), v)
   }

   fun get(address : ULongPointer) : ULong {
      val r = unsafe.getLong( address.toPointer().toUnsafePointer())
      return r.toULong()
   }

   fun put(address : ULongPointer, v : ULong) : Unit {
      unsafe.putLong( address.toPointer().toUnsafePointer(), v.toLong())
   }

   fun get(address : FloatPointer) : Float {
      val r = unsafe.getFloat( address.toPointer().toUnsafePointer())
      return r
   }

   fun put(address : FloatPointer, v : Float) : Unit {
      unsafe.putFloat( address.toPointer().toUnsafePointer(), v)
   }

   fun get(address : DoublePointer) : Double {
      val r = unsafe.getDouble( address.toPointer().toUnsafePointer())
      return r
   }

   fun put(address : DoublePointer, v : Double) : Unit {
      unsafe.putDouble( address.toPointer().toUnsafePointer(), v)
   }

}