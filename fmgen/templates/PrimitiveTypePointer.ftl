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
fun Pointer.to${primitive_type}Pointer(): ${primitive_type}Pointer {
   return ${primitive_type}Pointer(this)
}

@kotlin.ExperimentalUnsignedTypes
public inline class ${primitive_type}Pointer(private val address: Pointer): Comparable<${primitive_type}Pointer> {
   fun toPointer(): Pointer = address

   var it: ${primitive_type} get() { assert(!isNull()); return memAccess.get(this) }
      set(v: ${primitive_type}) { assert(!isNull()); memAccess.put(this, v) }
   operator fun get(i: PointerOffset): ${primitive_type} = memAccess.get(this + i)
   operator fun set(i: PointerOffset, v: ${primitive_type}): Unit = memAccess.put(this + i, v)

   fun isNull(): Boolean = address == NULL
   operator fun not(): Boolean = address == NULL
   operator fun inc(): ${primitive_type}Pointer = ${primitive_type}Pointer(address + (1L*${size_bytes}))
   operator fun dec(): ${primitive_type}Pointer = ${primitive_type}Pointer(address - (1L*${size_bytes}))           
   operator fun plus(v: PointerOffset): ${primitive_type}Pointer = ${primitive_type}Pointer(address + (v*${size_bytes}))
   operator fun minus(v: PointerOffset): ${primitive_type}Pointer = ${primitive_type}Pointer(address - (v*${size_bytes}))   
   operator fun minus(v: ${primitive_type}Pointer): PointerOffset = (address.toUnsafePointer() - v.address.toUnsafePointer()) / ${size_bytes}
   override operator fun compareTo( p: ${primitive_type}Pointer ): Int = this.address.compareTo( p.address)   
}

class ${primitive_type}PointerIterator (val begin : ${primitive_type}Pointer, val end: ${primitive_type}Pointer): Iterator<${primitive_type}>
{
   private var current: ${primitive_type}Pointer = begin
   
   init {
      assert( begin <= end)
   }
   
   override fun hasNext(): Boolean {
      return current < end
   }
 
   override fun next(): ${primitive_type} {
      val v = current.it
      current++
         return v
   }   
}



@kotlin.ExperimentalUnsignedTypes
fun PrimitiveArraysAllocator.allocate${primitive_type}PointerArray( itemCount: Size, zeroMem: Boolean = PrimitiveArraysAllocator.zeroMem ): ${primitive_type}Pointer {
   assert(itemCount > 0L)
   
   val mem = this.rawAllocator.allocate( itemCount, ${size_bytes}L, zeroMem )
   return ${primitive_type}Pointer(mem)
}

@kotlin.ExperimentalUnsignedTypes
fun PrimitiveArraysAllocator.free( pointerToArray: ${primitive_type}Pointer) {
   assert(!pointerToArray.isNull())
   
   this.rawAllocator.free( pointerToArray.toPointer() )
}