# Advanced Kotlin: pointers -with KPointers

![KPointer pointers for Kotlin](README/headerImage.png)

## What KPointers is

**KPointers** is a library for low-level access to memory that improves quite a bit on the use of ``Unsafe``, the only way the JDK provids. Using ``Unsafe``, which sees a pointer as ``Long`` no matter what it points to, is _daunting_: **KPointers** improve on this by providing _typed pointers_ for the different primitive types, allowing the compiler to find many errors at compile time. And it does so without the added overhead JVM objects introduce -something real bad for such low-level and frquently used objects.

If you are into hard low-level programming, you'll love **KPointers**.

## Introduction

Kotlin for the JVM does not provide _pointers_, but **KPointers** _does_ provide pointers that for all intents and purposes look and act like _C/C+pointers_ -performance included.

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

Since we want to support idiomatic Kotlin, we can write that more compactly, as follows:

``` Kotlin
   fun sum( begin: LongPointer, end: LongPointer): Long {
      var sum : Long = 0L
      for( v in LongPointerIterator(begin,end) ) {
         sum += v
      }
      return sum
   }
```

Now, if you are into low-level programming with Kotlin, you'll know how you came to get a memory block that starts at ``begin`` and ends at ``end``. Well, maybe you've got it via JNI, and it points to shared memory you allocated in a C++ application, or you allocated it using ``Unsafe.allocateMemory`` for some reason: up to you!

Going back to the "as powerful as C/C++" idea, we support the following functionality (the same as for C/C++ pointers!):

- Dereference a pointer = read/write a value: ``p.it = value`` and ``value = p.it`` read and write to a memory address.
- Array-like access: ``p[5] = value`` or ``p[-3] = value``
- Increment and decrement pointer: ``p++`` and ``p--``
- Increment and decrement a pointer by an arbitrary quantity: ``p += 3``, ``p = p2 - 3``, etc
- Subtract pointers: ``distance = p1 - p2``
- Pointer comparison: ``p1 < p2``, ``p1 == p2``, etc
- Easy check for null: ``p isNull`` or ``if( !p)``

As you can see, all operations in a C/C++ pointer are implemented in KPointers pointers.
We provide a pointer class for every primitive type: ``LongPointer``, ``CharPointer``, ``UBytePointer``, etc.

Besides, we provide an iterator class for every pointer type to be able to write more idiomatic Kotlin code, such as writing easier to read _for_ loops -as illustrated above.

## Why pointers

Kotlin runs on top of JVM (yes, there are other options), and one of the cornerstones of the JVM design was to avoid raw memory access, and that meant no pointers. Then, why _reintroduce_ pointers?

The answer is: _to get better performance_ both when it comes to computing _speed_ and to improve on _space_ usage. How is it so? Well, when it comes to speed, handling huge amounts of data (we are talking hundreds of gigabytes of memory here, or even terabytes) means the garbage collector will turn crazy quite often. As an example, in my 32 cores/64 threads workstation it is relatively common to get 50% or more of CPU usage for just one of thoseprograms. There goes the _speed_ thing out of the window.

Besides, Java objects have a lot of overhead, and small objects can consume much more space than they really need due to having to containn internal a pointer to their class, etc. In fact, for a single ``Character`` object you get a 16 bytes overheads in may JVM. And there goes the _size_ thing out of the window too.

Both things can be ameliorated by using _raw memory_ (the ``Unsafe`` class provides us with a way to allocate raw memory), also called _off-heap memory_: if we define the data structure directly on top of that raw memory the compiler will add no overhead, and we'll get better space usage. And the Garbage Collector will not get in the way and trash with our memory, as raw memory is not owned by it -but, yes,  we will have to care about it in one way or other. All this requires pointers, and **KPointers** provides a much safer way to handle them, as opposed to the way the JVM handles them. If you don't believe this _define the data structure directly on top of raw memory_ thing is feasible, check our [**KStructs**](https://github.com/pagullo-soft-dev/kstructs) library: it does _exactly_ that, and was one of the main reasons to implement **KPointers** itself as an independent library.

There is a _third advantage_ to using raw memory: very advanced applications can benefit from using direct memory access. As an example, I've got 3.1GB/s speed in I/O operations with NVME SSDs with a custom I/O implementation that required direct memory access. The fastest I/O using Java's built-in high performance file access was around 2GB/s. That was a huge speed gain.

Yes, these are scenarios that will not arise in everyday use, unless you are in charge of writing one of those high performance libraries we mere mortals use. But when you need it, you'll need **KPointers** badly.

Finally, there is a _fourth advantage_: Java arrays, a basic foundation for many structures, are limited in length to ``Integer.MAX_INT``, but **KPointers** can allocate much bigger arrays, being limited only by the maximum memory that can be allocated at a given time -the way it should be. Again, this might be relevant only for massive data, but the feature is there.

## Running the examples

To see the list of existing examples you can run from the command line, execute ``gradlew tasks --group Application`` from the command line

To run example ``Xxx``, use the existing _runXxxExample_ task executing ``gradlew runXxxExample`` from the command line

All examples source code is located under _src/main/pointers/examples_: check it.

## License

**KPointers** is provided under the [LPGL 3.0 license](https://opensource.org/licenses/LGPL-3.0)
