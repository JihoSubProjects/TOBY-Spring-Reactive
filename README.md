# Toby의 Reactive Functional Programming

>   개념
>   - Duality
>   - Observer Pattern
>   - Reactive Streams : Java9 API 표준

## Duality

>   에릭 마이어 (Duality, 쌍대성)
>   - Iterable <----> Observable
>   - Pulling 방식 : next()로 계속 다음 

### Iterable vs Observable

| Iterable | Observable              |
| -------- | ----------------------- |
| Pull     | Push                    |
| `next()` | `notifyObservers(args)` |

### Observable의 장단점

#### 장점

-   별도의 Thread로 동작할 때 여러 이점이 있다.
-   다수의 Subscriber에게 BroadCasting이 가능하다.

#### 단점

-   Complete이라는 개념을 적용하기 어렵다.
    >   언제까지 계속 돌아가는데? 
-   Error/Exception 처리가 어렵다.
    >   단순하게 생각하면 try-catch 하면 되지 싶다.
    >   하지만, 어떻게 복구하고 처리할 것인지 생각해야 한다.

이러한 단점을 극복해서 나온 것이 Reactive Observer라고 한다.
