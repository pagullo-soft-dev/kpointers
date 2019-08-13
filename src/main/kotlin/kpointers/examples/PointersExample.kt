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

package com.softwarementors.kpointers.examples

import com.softwarementors.kpointers.malloc.UnsafeBackedAllocator
import com.softwarementors.kpointers.malloc.PrimitiveArraysAllocator
import com.softwarementors.kpointers.*

@kotlin.ExperimentalUnsignedTypes
fun main( args : Array<String> ) {
   println( title )
   println( "Example: typical pointer operations by CharPointer, a C-like char* equivalent in Kotlin")
   println()
   
   println( "Starting example execution...")
   // The number of items in the raw memory array, which will hold chars from 'a' to 'z',
   // both included
   val itemsCount = ('z' - 'a' + 1).toLong()
   val allocator = PrimitiveArraysAllocator.unsafeAllocator
   
   val array = allocator.allocateCharPointerArray( itemsCount )
   try {
      println( "Running code illustrating indexed pointer write access...: filling raw memory array")
      for( i in 0..itemsCount ) {
         array[i] = 'a' + i.toInt()
      }
      
      println( "Running code illustrating pointer increment, comparison and read access...")
      print( "Characters in raw memory array: '")
      var current = array
      val last = array + itemsCount
      while( current < last ) {
         print( current.it )
         current++
      }
      println("'")

      println( "Running code illustrating pointer subtraction...")
      val diff = last - array
      println( "Number of items in the array: $diff, from '${array[0]}' to '${array[diff-1]}'")

      println( "Running code illustrating pointer write access...")
      array[2] = 'C'
      println( "Now the third item in the array is '${array[2]}' -it was 'c'")
    }
   finally {
      allocator.free(array)
   }
}
