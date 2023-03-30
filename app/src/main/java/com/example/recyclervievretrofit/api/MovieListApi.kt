package com.example.recyclervievretrofit.api
import com.example.recyclervievretrofit.models.MovieList
import com.example.recyclervievretrofit.models.PagedMovieList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

//API кинопоиска

interface MovieListApi{
    //Запрос на получение фильмов по конкретному году и месяцу
    @Headers("X-API-KEY: $api_key")
    @GET("api/v2.2/films/premieres")
    suspend fun movies(@Query("year") year:Int,@Query("month") month:String):MovieList

    //Запрос на получение страницы с ТОП 250 фильмами по версии кинопоиск
    @Headers("X-API-KEY: $api_key")
    @GET("api/v2.2/films/top?type=TOP_250_BEST_FILMS")
    suspend fun topList(@Query("page") page:Int):PagedMovieList

    //Для работы с сервисом необходим токен, который выдают при регистрации. Данный токен нужно передавать в хеддере
    private companion object{
        private const val api_key = "6a1998b7-b3e3-4f3b-a248-400968c46b3b"
    }
}

//Формируем инстанс ретрофита:
    val retrofit = Retrofit.Builder()
        .client(
            OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }).build()
        )
        .baseUrl("https://kinopoiskapiunofficial.tech/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create<MovieListApi>(MovieListApi::class.java)

//Месяц в GET запросе movies должен быть прописан строго определенныйм образом.
enum class AvailableMonths (private val month: String){
    JANUARY("JANUARY"),
    FEBRUARY("FEBRUARY"),
    MARCH("MARCH"),
    APRIL("APRIL"),
    MAY("MAY"),
    JUNE("JUNE"),
    JULY("JULY"),
    AUGUST("AUGUST"),
    SEPTEMBER("SEPTEMBER"),
    OCTOBER("OCTOBER"),
    NOVEMBER("NOVEMBER"),
    DECEMBER("DECEMBER");
}
