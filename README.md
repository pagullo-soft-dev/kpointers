# Advanced Kotlin: pointers -with KPointers

![KPointer pointers for Kotlin](README/headerImage.png)

## What KPointers is

**KPointers** is a library for low-level access to memory that improves quite a bit on the use of ``Unsafe``. Using ``Unsafe``, which sees a pointer as ``Long`` no matter what it points to, is _daunting_: **KPointers** improve on this by providing _typed pointers_ for the different primitive types, allowing the compiler to find many errors at compile time. And it does so without the added overhead JVM objects introduce -something real bad for such low-level and frquently used objects.

If you are into hard low-level programming, you'll love **KPointers**.

## Introduction

Of course, Kotlin does not provide _pointers_, but **KPointers** _does_ implement pointers for Kotlin, pointers that for all intents and purposes look and act like _C/C+pointers_ -performance included.

To prove the point, here is actual  _Kotlin_ code for a function that sums all integers in a memory block that starts at address ``first`` and ends at address ``last``:

``` Kotlin
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
```

If you program in C++, you'll notice that this is very similar to the ``accumulate`` function in the _Standard Template Library_.

As a matter of fact, the following pointer functionality is provided for pointer ``p``:

- Dereference a pointer = read/write a value: ``p.it = value`` and ``value = p.it`` read and write to a memory address.
- Array-like access: ``p[5] = value`` or ``p[-3] = value``
- Increment and decrement pointer: ``p++`` and ``p--``
- Increment and decrement a pointer by an arbitrary quantity: ``p += 3``, ``p = p2 - 3``, etc
- Subtract pointers: ``distance = p1 - p2``
- Pointer comparison: ``p1 < p2``, ``p1 == p2``, etc
- Easy check for null: ``p isNull`` or ``if( !p)``

As you can see, all operations in a C/C++ pointer are implemented in KPointers pointers.
We provide a pointer class for every primitive type: ``LongPointer``, ``CharPointer``, ``UBytePointer``, etc.

## Why pointers

Kotlin runs on top of JVM (yes, there are other options), and one of the cornerstones of the JVM design was to avoid raw memory access, and that meant no pointers. Then, why _reintroduce_ pointers?

The answer is: _to get better performance_ both when it comes to computing _speed_ and to improve on _space_ usage. How is it so? Well, when it comes to speed, handling huge amounts of data (we are talking hundreds of gigabytes of memory here, or even terabytes) means the garbage collector will turn crazy quite often. As an example, in my 32 cores/64 threads workstation it is relatively common to get 50% or more of CPU usage for such programs. There goes the _speed_ thing out of the window.

Besides, Java objects have a lot of overhead, and small objects can consume much more space than they really need due to having to contain a pointer to their class, to memory alignment, etc. If we work with collections (how not to do that when processing huge amounts of data?), then you'll find that the JDK implementation for many collections has a lot of overhead, too. And there goes the _size_ thing out of the window too.

Both things can be ameliorated by using _raw memory_ (yes, the ``Unsafe`` class provides us with a way to allocate raw memory!), also called _off-heap memory_: we define the data structure directly, so the compiler will add nothing, so we get better space usage. And the Garbage Collector will not get in the way and trash with our memory, as raw memory is not owned by it -but, we will have to care about it in one way or other. All this requires pointers, and **KPointers** provides a much safer way to handle them, as opposed to the way the JVM handles them.

There is a _third advantage_ to using raw memory: very advanced applications can benefit from using direct memory access. As an example, I've got 3.1GB/s speed in I/O operations with NVME SSDs with a custom I/O implementation that required direct memory access, whereas the fastest I/O using Java's built-in high performance file access was around 2GB/s. That meant a 33% speed gain.

Yes, these are scenarios that will not arise in everyday scenarios, unless you are in charge of writing one of those high performance libraries we mere mortals use. But when you need it, you need something like **KPointers** badly.

Finally, there is _fourth advantage_: Java arrays, a basic foundation for many structures, are limited in length to ``Integer.MAX_INT``,
but **KPointers** can allocate much bigger arrays, being limited only by the maximum memory that can be allocated at a given time -the way it should be. Again, this might be relevant only for massive data, but the feature is there.

## Running the examples

To see the list of existing examples you can run from the command line, execute ``gradlew tasks --group Application`` from the command line

To run example ``Xxx``, use the _runXxxExample_ task executing ``gradlew runXxxExample`` from the command line

All examples source code is located under _src/main/pointers/examples_: check it.

## License

**KPointers** is provided under the [LPGL 3.0 license](https://opensource.org/licenses/LGPL-3.0)
