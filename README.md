# RxGrpc-kt-extension
This is a kotlin extension which allows ReactiveX style Grpc Call.

It also removes Grpc Message Builder boilerplates. 

# Usage
with rx extension
```kotlin
val userStub = UserGrpc.newBlockingStub(channel)
userStub::getUserProfile
  // make it reactive
  .rx {
      // parameters go here, without Builder boilerplates
      userId = 123
      deviceType = "Android device"
      timeStmp = System.currentTimeMillis()
  }
  // maybe threading
  // maybe chaining
  .subscribe(
      { doSomeThing(it) },
      { handleError(it) }
  )
```

without rx extesion
```kotlin
val userStub = UserGrpc.newBlockingStub(channel)
try {
  val resp: GetUserProfileResponse = userStub.getUserProfile(
      //parameters go here, with Builder boilerplates
      GetUserProfileRequestBuilder.newBuilder()
        .setUserId(123)
        .setDeviceType("Android device")
        .setTimeStmp(System.currentTimeMillis())
        .build()
  )
  // no easy threading
  // no easy chaining
  doSomeThing(resp)
} catch (Throwable e) {
  handleError(e)
}
```
