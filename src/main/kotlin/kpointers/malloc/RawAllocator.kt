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

package com.softwarementors.kpointers.malloc

import com.softwarementors.kpointers.impl.unsafe
import com.softwarementors.kpointers.*

abstract class RawAllocator() {
   companion object {
      val zeroMem = false
   }
   
   fun allocate( memSize : Size) : Pointer = allocate(memSize, zeroMem)
   fun allocate(memSize : Size, n : Size) : Pointer = allocate(memSize, n, zeroMem )
   fun allocate(memSize : Size, n : Size, zeroMem : Boolean) : Pointer = allocate(memSize * n, zeroMem )
   abstract fun allocate( memSize : Size, zeroMem : Boolean) : Pointer
   abstract fun free( mem : Pointer)
   
   protected fun zeroFill( mem : Pointer, memSize : Size ) {
      unsafe.setMemory(mem.toUnsafeLongAsPointer(), memSize.toLong(), 0) 
   } 
}