package kynvfhn.fsoft.coroutinedemo.Coroutines_Study

import kotlinx.coroutines.*


fun main() {
    nonRunBooKing()
    runBooking()

    withContextTest()
    unconfinedDispatcher()
}

fun unconfinedDispatcher() {

}

fun withContextTest() {
    newSingleThreadContext("thread1").use { cxt1 ->
        // tạo một context là cxt1 chứ chưa launch coroutine
        // cxt1 sẽ có 1 element là dispatcher quyết định couroutine sẽ chạy trên 1 thread tên là thread1
        println("ctx 1 = ${Thread.currentThread().name}")

        newSingleThreadContext("thread2").use { cxt2 ->
            // tạo một context là cxt1 chứ chưa launch coroutine
            // cxt1 sẽ có 1 element là dispatcher quyết định couroutine sẽ chạy trên 1 thread tên là thread2
            println("ctx2 = ${Thread.currentThread().name}")

            // bắt đầu chạy coroutin với context là ctx1
            runBlocking(cxt1) {
                // coroutine đang chạy trên context ctx1 và trên thread thread1
                println("Started in ctx1 - ${Thread.currentThread().name}")

                // sử dụng with context để chuyển đổi context từ cxt1 qua cxt2
                withContext(cxt2) {
                    // coroutine đang chạy với context ctx2 và trên thread thread2
                    println("Working in ctx2 - ${Thread.currentThread().name}")
                }

                // coroutine đã thoát ra block withContext nên sẽ chạy lại với context ctx1 và trên thread thread1
                println("Back to ctx1 - ${Thread.currentThread().name}")
            }
        }
        println("out of ctx2 block - ${Thread.currentThread().name}")
    }
    println("out of ctx1 block - ${Thread.currentThread().name}")
}

fun runBooking() {
    runBlocking {
        println("ptit")
        delay(5000L)
    }
    println("kynv1")
}


fun nonRunBooKing() {
    GlobalScope.launch { // chạy một coroutine trên background thread
        delay(3000L) // non-blocking coroutine bị delay 10s
        println("World,") // print từ World ra sau khi hết delay
    }
    println("Hello,") // main thread vẫn tiếp tục chạy xuống dòng code này trong khi coroutine vẫn đang bị delay 10s
    Thread.sleep(20000L) // block main thread 2s
    println("Kotlin")
}