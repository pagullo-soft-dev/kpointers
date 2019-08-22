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

/**************************************************************************
File generated automatically with fmgen: DO NOT modify it manually
**************************************************************************/

package com.softwarementors.kpointers

import com.softwarementors.kpointers.PrimitiveArraysAllocator
import com.softwarementors.kpointers.Pointer
import com.softwarementors.kpointers.PointerOffset
import com.softwarementors.kpointers.NULL
import com.softwarementors.kpointers.memAccess

@kotlin.ExperimentalUnsignedTypes
fun Pointer.toULongPointer(): ULongPointer {
   return ULongPointer(this)
}

@kotlin.ExperimentalUnsignedTypes
public inline class ULongPointer(private val address: Pointer): Comparable<ULongPointer> {
   fun toPointer(): Pointer = address

   var it: ULong get() { assert(!isNull()); return memAccess.get(this) }
      set(v: ULong) { assert(!isNull()); memAccess.put(this, v) }
   operator fun get(i: PointerOffset): ULong = memAccess.get(this + i)
   operator fun set(i: PointerOffset, v: ULong): Unit = memAccess.put(this + i, v)

   fun isNull(): Boolean = address == NULL
   operator fun not(): Boolean = address == NULL
   operator fun inc(): ULongPointer = ULongPointer(address + (1L*8))
   operator fun dec(): ULongPointer = ULongPointer(address - (1L*8))           
   operator fun plus(v: PointerOffset): ULongPointer = ULongPointer(address + (v*8))
   operator fun minus(v: PointerOffset): ULongPointer = ULongPointer(address - (v*8))   
   operator fun minus(v: ULongPointer): PointerOffset = (address.toUnsafePointer() - v.address.toUnsafePointer()) / 8
   override operator fun compareTo( other: ULongPointer ): Int = this.address.compareTo( other.address)   
}

@kotlin.ExperimentalUnsignedTypes
class ULongPointerIterator (val begin : ULongPointer, val end: ULongPointer): Iterator<ULong>
{
   private var current: ULongPointer = begin
   
   init {
      assert( begin <= end)
   }
   
   override fun hasNext(): Boolean {
      return current < end
   }
 
   override fun next(): ULong {
      val v = current.it
      current++
         return v
   }   
}


@kotlin.ExperimentalUnsignedTypes
fun PrimitiveArraysAllocator.allocateULongPointerArray( itemCount: Size, zeroMem: Boolean = PrimitiveArraysAllocator.zeroMem ): ULongPointer {
   assert(itemCount > 0L)
   
   val mem = this.rawAllocator.allocate( itemCount, 8L, zeroMem )
   return ULongPointer(mem)
}

@kotlin.ExperimentalUnsignedTypes
fun PrimitiveArraysAllocator.free( pointerToArray: ULongPointer) {
   assert(!pointerToArray.isNull())
   
   this.rawAllocator.free( pointerToArray.toPointer() )
}