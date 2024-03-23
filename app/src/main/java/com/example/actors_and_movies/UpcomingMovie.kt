package com.example.actors_and_movies  //name of the package we are working with

//implement the packages we will be using
import android.support.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//Following sample code shows the api call that needs to be made: https://api.themoviedb.org/3/movie/upcoming?language=en-US&page=1&api_key="API-KEY-GOES-HERE" --> I need to see what the sample response looks like in order to determine the data class

@android.support.annotation.Keep
@Serializable
data class UpcomingMovie(
    @SerialName("title")  //not custom, already defined in JSON response
    val name : String?,

    @SerialName("id")  //not custom, already defined in JSON response (id is integer datatype)
    val id : Int?,

    @SerialName("backdrop_path")  //retrieve the backdrop path to get the image view, initialized to null
    val movieBackdropPath : String? = null,

    @SerialName("poster_path")
    val poster_path : String? = null,

    @SerialName("vote_average")
    val movieRating : Int?,

    @SerialName("byline")  //contains custom name and class type
    val byline : Byline?,

    @SerialName("headline")  //the values defined here do not hold restrictions, they are custom defined (referring to the content within the strings), and they correspond to the datatype (which is also custom defined but those must adhere to the structure of the json response file)
    val headline : Headline? ,

    @SerialName("overview")
    val movie_overview : String?,

    @SerialName("multimedia")
    val multimedia : List<MultiMedia>?,  //create a list of UpcomingMovies
) : java.io.Serializable
//val mediaImageUrl = "https://www.nytimes.com/${multimedia?.firstOrNull { it.url != null }?.url ?: ""}"


@Keep
@Serializable
data class UpcomingMovies_API_Response(
    @SerialName("response")
    val response : BaseResponse?  //this class is also custom defined (think of it as a nested class definition)
)

@Keep
@Serializable
data class MultiMedia(
    @SerialName("url") //assuming there's a url pointing to the movie image (again, need to check the json response to understand the structure and adjust as needed)
    val url : String?
) : java.io.Serializable

@Keep
@Serializable
data class Headline(
    @SerialName("main")
    val main : String?
) : java.io.Serializable

@Keep
@Serializable
data class Byline(
    @SerialName("original")
    val original : String? //assuming it's a string datatype
) : java.io.Serializable


//lastly, define the list based data model
@Keep
@Serializable
data class BaseResponse(
    @SerialName("docs")
    val docs: List<UpcomingMovie>?
)

//additional ifnormation in the data model pertaining to the movies, may or may not be needed

@Keep
@Serializable
data class ApiResponse(
    @SerialName("dates") val dates: Dates,
    @SerialName("page") val page: Int,
    @SerialName("results") val results: List<UpcomingMovie>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)
@Keep
@Serializable
data class Dates(
    @SerialName("maximum") val maximum: String,
    @SerialName("minimum") val minimum: String
)

/**
 * Code from flixter plus:
 *
 * package com.example.flixterplus
 *
 * import com.google.gson.annotations.SerializedName
 *
 * //Note: Base path is the following: "https://image.tmdb.org/t/p/w500/" --> add this path prior to the backdrop/poster path to display the image
 * //This is the movie data model file, refer to the json file for the sample response
 * class LatestMovies {
 *     //ensure that the strings within @SerializedName matches the JSON response, which I have included at the bottom as reference
 *     @SerializedName("id")  //retrieve the movie id from the json response
 *     var id = 0  //initialize the id to hold the value 0
 *
 *     @SerializedName("backdrop_path")  //retrieve the backdrop path to display in the Image view
 *     var movieBackdropPath : String? = null //set the variable datatype to string and set the value to null
 *
 *     //define the movie overview
 *     @SerializedName("overview")
 *     var movieOverview : String? = null  //extract the movie overview, initialize to null
 *
 *     @SerializedName("poster_path")  //retrieve the poster path, experiment and see which one is the actual image path
 *     var moviePosterPath : String? = null
 *
 *     @SerializedName("title")  //retrieve the official movie title
 *     var movieTitle : String? = null
 *
 *     @SerializedName("original_title")  //retreive the original title of the movie
 *     var movieOriginalTitle : String? = null
 *
 *     @SerializedName("vote_average")  //extract the movie rating
 *     var movieRating : String? = null
 * }
 *
 *
 *
 * */