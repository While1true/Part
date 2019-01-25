package com.part.arouteTest.mvp.model;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * by ckckck 2019/1/16
 * <p>
 * life is short , bugs are too many!
 */
public interface IService {
    @GET("article/list/{page}/json")
    Observable<Object> getData(@Path("page")String page);
}
