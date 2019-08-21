package com.softwarementors.kpointers

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.AfterEach
import com.softwarementors.kpointers.unsafeAllocator
import com.softwarementors.kpointers.impl.unsafe

@kotlin.ExperimentalUnsignedTypes
class PrimitiveTypeXxxPointerTest {

   val outstandingAllocations = mutableListOf<LongPointer>()
   fun allocArray100() : Pointer {
      val r = unsafeAllocator.allocateLongPointerArray(100)
      outstandingAllocations.add(r)
      return r.toPointer()
   }
      
   @AfterEach
   fun afterEach() {
      outstandingAllocations.forEach { unsafeAllocator.free(it)}
   }
   
   private val SIZE_BYTES : Long = Long.SIZE_BYTES.toLong()
   private fun toPtrTypeUnderTest( p : Pointer) : LongPointer {
      return p.toLongPointer()
   }
   
   @Test
   fun test_set() {
      val it = allocArray100()
      var p = toPtrTypeUnderTest(it)
      p.it = 99L
      assertEquals( 99L, unsafe.getLong(it.toUnsafePointer()))
   }
   
   @Test
   fun test_get() {
      val it = allocArray100()
      var p = toPtrTypeUnderTest(it)
      unsafe.putLong(it.toUnsafePointer(), 893L)
      assertEquals( 893L, p.it)
   }
   
   @Test
   fun test_set_indexed() {
      val it = allocArray100()
      var p = toPtrTypeUnderTest(it)
      p[10] = 99L
      assertEquals( 99L, unsafe.getLong(it.toUnsafePointer()+(10*Long.SIZE_BYTES)))
   }

   @Test
   fun test_get_indexed() {
      val it = allocArray100()
      var p = toPtrTypeUnderTest(it)
      unsafe.putLong(it.toUnsafePointer()+(11*Long.SIZE_BYTES), 893L)
      assertEquals( 893L, p[11])
   }
   
   @Test
   fun test_not() {
      val it = allocArray100()
      var p = toPtrTypeUnderTest( it)
      assertFalse(!p)
      
      p = LongPointer(NULL)         
      assertTrue(!p)
   }
   
   @Test
   fun test_inc() {
      val it = allocArray100()
      var p = toPtrTypeUnderTest(it)
      p++
      assertEquals( p.toPointer(), it + SIZE_BYTES)
      
      p = toPtrTypeUnderTest(it)
      p += 10
      assertEquals( p.toPointer(), it + (SIZE_BYTES*10))
   }

   @Test
   fun test_dec() {
      val it = allocArray100()
      var p = toPtrTypeUnderTest(it)
      p--
      assertEquals( p.toPointer(), it - SIZE_BYTES)

      p = toPtrTypeUnderTest(it)
      p -= 10
      assertEquals( p.toPointer(), it - (SIZE_BYTES*10))
   }

   @Test
   fun test_plus() {
      val it = allocArray100()
      val p = toPtrTypeUnderTest(it)
      val p2 = p + 2
      assertFalse( p2.toPointer() == it)
      assertFalse( p2 == p)
      assertEquals( p2.toPointer(), (it+(2*SIZE_BYTES)))
      assertEquals( p2, p+2)
   }

   @Test
   fun test_minus() {
      val it = allocArray100()
      val p = toPtrTypeUnderTest(it)
      val p2 = p - 2
      assertFalse( p2.toPointer() == it)
      assertFalse( p2 == p)
      assertEquals( p2.toPointer(), (it-(2*SIZE_BYTES)))
      assertEquals( p2, p-2)
   }
   
   @Test
   fun test_minus_forPointer() {
      val it = allocArray100()
      val p = toPtrTypeUnderTest(it)
      val p2 = p + 33

      assertEquals( 33, p2 - p)
      assertEquals( -33, p - p2)
   }

   @Test
   fun test_compareTo() {
      val it = allocArray100()
      val start = toPtrTypeUnderTest(it)
      val end = toPtrTypeUnderTest(it + SIZE_BYTES)
      val start2 = toPtrTypeUnderTest(it)
      assertEquals( start, start2)
      assertTrue( start == start2)
      assertTrue( start != end)
      assertTrue( start < end)
      assertTrue( start <= end)
      assertTrue( end > start)
      assertTrue( end >= start )
   }
   
   @Test
   fun test_PrimitiveArrays_createXxxPointerArray() {
      val allocator = unsafeAllocator
      val mem : LongPointer = allocator.allocateLongPointerArray(100L, true)
      try {
         // Check this is zero initiated
         var current = mem
         val end = mem + 100L
         while( current < end ) {
            assertEquals( current.it, 0L)
            current++
         }
      }
      finally {
         allocator.free(mem)
      }
   }
   
   
}