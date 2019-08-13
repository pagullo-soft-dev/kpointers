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
import java.time.LocalDateTime
import java.time.Duration

@kotlin.ExperimentalUnsignedTypes
fun main( args : Array<String> ) {
   val _1MB = 1024L * 1024L
   val itemsCount : Long = 32L * _1MB // 256 MB 
   val memMBSize = itemsCount * Long.SIZE_BYTES / (1024*1024)

   println( title )
   println( "Example: implementing a Sum function to add all Longs in $memMBSize MB of raw allocated memory")
   println()
   
   println( "Starting example execution...")
   // The number of items in the raw memory array, which will hold chars from 'a' to 'z',
   // both included
   val allocator = PrimitiveArraysAllocator.unsafeAllocator
   
   val array = allocator.allocateLongPointerArray( itemsCount )
   try {
      println( "Filling raw memory with data")
      for( i in 0 until itemsCount ) {
         array[i] = i.toLong()
      }
      
      val sum = sum(array, array + itemsCount)
      val start = System.nanoTime()
      println( "Sum of integers from 1 to ${itemsCount-1}: $sum" )
      val end = System.nanoTime()
      val elapsedTime = (end-start) / 1_000 // Microsecons
      println( "Elapsed time: $elapsedTime microseconds")
    }
   finally {
      allocator.free(array)
   }
}

@kotlin.ExperimentalUnsignedTypes
fun sum( first: LongPointer, last: LongPointer) : Long {
   var sum : Long = 0L
   var current = first
   // While we do not arrive at the last addresss...
   while( current < last ) {
      // Read the value using the pointer
      val v = current.it
      sum += v
      // Make the pointer point to the next address
      current++
   }
   return sum
}

