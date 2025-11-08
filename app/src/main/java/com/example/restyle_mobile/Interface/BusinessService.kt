package Interface

import Beans.Business
import Beans.Businesses
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BusinessService {

    @GET("api/v1/businesses")
    fun getBusinesses(): Call<List<Businesses>>

    @GET("api/v1/businesses/{id}")
    fun getBusinessById(@Path("id") businessId: Int): Call<Businesses>
}