package com.pt.rx.test;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RxTest1 {

	public static void test1() {
		Observable<String> obs = Observable.create(new ObservableOnSubscribe<String>() {
			@Override
			public void subscribe(@NonNull ObservableEmitter<@NonNull String> emitter) throws Throwable {
				emitter.onNext("hello");
				emitter.onComplete();
			}
		});

		obs.subscribe(s -> {
			System.out.println(s);
		});
	}

	public static void test2() {
		Observable.just(1, 2, 3).map(s -> s + 1).subscribe(s -> {
			System.out.println(s);
		});
	}

	public static void test3() {
		Observable.interval(0, 1, TimeUnit.SECONDS)
		.blockingSubscribe(System.out::println);
	}

	public static void skip() {
		Observable.interval(0, 1, TimeUnit.SECONDS)
		.skip(3)
//		.skipLast(4)
		.blockingSubscribe(System.out::println);
	}
	
	public static void skipLast() {
		Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
		.skip(3)
		.skipLast(4)
		.blockingSubscribe(System.out::println);
	}
	
	public static void debounce() {
		Observable.create(emitter->{
			emitter.onNext("a");
			
			Thread.sleep(1_500);
			emitter.onNext("b");
			
			Thread.sleep(500);
			emitter.onNext("c");
			
			Thread.sleep(250);
			emitter.onNext("d");
			
			Thread.sleep(2_000);
			emitter.onNext("e");
			
			emitter.onComplete();
		})
		.subscribeOn(Schedulers.io())
		.debounce(1,TimeUnit.SECONDS)
		.blockingSubscribe(item -> System.out.println(item),
				Throwable::printStackTrace,
				()->System.out.println("completed"));
	}

	public static void main(String[] args) {

		debounce();
	}
}
