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
fun Pointer.toFloatPointer(): FloatPointer {
   return FloatPointer(this)
}

@kotlin.ExperimentalUnsignedTypes
public inline class FloatPointer(private val address: Pointer): Comparable<FloatPointer> {
   fun toPointer(): Pointer = address

   var it: Float get() { assert(!isNull()); return memAccess.get(this) }
      set(v: Float) { assert(!isNull()); memAccess.put(this, v) }
   operator fun get(i: PointerOffset): Float = memAccess.get(this + i)
   operator fun set(i: PointerOffset, v: Float): Unit = memAccess.put(this + i, v)

   fun isNull(): Boolean = address == NULL
   operator fun not(): Boolean = address == NULL
   operator fun inc(): FloatPointer = FloatPointer(address + (1L*4))
   operator fun dec(): FloatPointer = FloatPointer(address - (1L*4))           
   operator fun plus(v: PointerOffset): FloatPointer = FloatPointer(address + (v*4))
   operator fun minus(v: PointerOffset): FloatPointer = FloatPointer(address - (v*4))   
   operator fun minus(v: FloatPointer): PointerOffset = (address.toUnsafePointer() - v.address.toUnsafePointer()) / 4
   override operator fun compareTo( other: FloatPointer ): Int = this.address.compareTo( other.address)   
}

@kotlin.ExperimentalUnsignedTypes
class FloatPointerIterator (val begin : FloatPointer, val end: FloatPointer): Iterator<Float>
{
   private var current: FloatPointer = begin
   
   init {
      assert( begin <= end)
   }
   
   override fun hasNext(): Boolean {
      return current < end
   }
 
   override fun next(): Float {
      val v = current.it
      current++
         return v
   }   
}


@kotlin.ExperimentalUnsignedTypes
fun PrimitiveArraysAllocator.allocateFloatPointerArray( itemCount: Size, zeroMem: Boolean = PrimitiveArraysAllocator.zeroMem ): FloatPointer {
   assert(itemCount > 0L)
   
   val mem = this.rawAllocator.allocate( itemCount, 4L, zeroMem )
   return FloatPointer(mem)
}

@kotlin.ExperimentalUnsignedTypes
fun PrimitiveArraysAllocator.free( pointerToArray: FloatPointer) {
   assert(!pointerToArray.isNull())
   
   this.rawAllocator.free( pointerToArray.toPointer() )
}