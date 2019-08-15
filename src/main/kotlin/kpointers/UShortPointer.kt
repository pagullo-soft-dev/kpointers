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
fun Pointer.toUShortPointer(): UShortPointer {
   return UShortPointer(this)
}

@kotlin.ExperimentalUnsignedTypes
public inline class UShortPointer(private val address: Pointer) {
   fun toPointer(): Pointer = address

   var it: UShort get() { assert(!isNull()); return memAccess.get(this) }
      set(v: UShort) { assert(!isNull()); memAccess.put(this, v) }
   operator fun get(i: PointerOffset): UShort = memAccess.get(this + i)
   operator fun set(i: PointerOffset, v: UShort): Unit = memAccess.put(this + i, v)

   fun isNull(): Boolean = address == NULL
   operator fun not(): Boolean = address == NULL
   operator fun inc(): UShortPointer = UShortPointer(address + (1L*2))
   operator fun dec(): UShortPointer = UShortPointer(address - (1L*2))           
   operator fun plus(v: PointerOffset): UShortPointer = UShortPointer(address + (v*2))
   operator fun minus(v: PointerOffset): UShortPointer = UShortPointer(address - (v*2))   
   operator fun minus(v: UShortPointer): PointerOffset = (address.toUnsafePointer() - v.address.toUnsafePointer()) / 2
   operator fun compareTo( p: UShortPointer ): Int = this.address.compareTo( p.address)   
}

@kotlin.ExperimentalUnsignedTypes
fun PrimitiveArraysAllocator.allocateUShortPointerArray( itemCount: Size, zeroMem: Boolean = PrimitiveArraysAllocator.zeroMem ): UShortPointer {
   assert(itemCount > 0L)
   
   val mem = this.rawAllocator.allocate( itemCount, 2L, zeroMem )
   return UShortPointer(mem)
}

@kotlin.ExperimentalUnsignedTypes
fun PrimitiveArraysAllocator.free( pointerToArray: UShortPointer) {
   assert(!pointerToArray.isNull())
   
   this.rawAllocator.free( pointerToArray.toPointer() )
}