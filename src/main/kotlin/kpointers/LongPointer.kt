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


import com.softwarementors.kpointers.malloc.PrimitiveArraysAllocator
import com.softwarementors.kpointers.Pointer
import com.softwarementors.kpointers.PointerOffset
import com.softwarementors.kpointers.NULL
import com.softwarementors.kpointers.memAccess

@kotlin.ExperimentalUnsignedTypes
fun Pointer.toLongPointer() : LongPointer {
   return LongPointer(this)
}

@kotlin.ExperimentalUnsignedTypes
public inline class LongPointer(private val address : Pointer) {
   fun toPointer() : Pointer = address

   var it : Long get() { assert(!isNull()); return memAccess.get(this) }
      set(v:Long) { assert(!isNull()); memAccess.put(this, v) }
   operator fun get(i : PointerOffset) : Long = memAccess.get(this + i)
   operator fun set(i : PointerOffset, v : Long) : Unit = memAccess.put(this + i, v)

   fun isNull() : Boolean = address == NULL
   operator fun not() : Boolean = address == NULL
   operator fun inc() : LongPointer = LongPointer(address + (1L*8))
   operator fun dec() : LongPointer = LongPointer(address - (1L*8))           
   operator fun plus(v : PointerOffset) : LongPointer = LongPointer(address + (v*8))
   operator fun minus(v : PointerOffset) : LongPointer = LongPointer(address - (v*8))   
   operator fun minus(v : LongPointer) : PointerOffset = (address.toUnsafeLongAsPointer() - v.address.toUnsafeLongAsPointer()) / 8
   operator fun compareTo( p : LongPointer ) : Int = this.address.compareTo( p.address)   
}

@kotlin.ExperimentalUnsignedTypes
fun PrimitiveArraysAllocator.allocateLongPointerArray( itemCount : Size, zeroMem : Boolean = PrimitiveArraysAllocator.zeroMem ) : LongPointer {
   assert(itemCount > 0L)
   
   val mem = this.rawAllocator.allocate( itemCount, 8L, zeroMem )
   return LongPointer(mem)
}

@kotlin.ExperimentalUnsignedTypes
fun PrimitiveArraysAllocator.free( pointerToArray : LongPointer) {
   assert(!pointerToArray.isNull())
   
   this.rawAllocator.free( pointerToArray.toPointer() )
}