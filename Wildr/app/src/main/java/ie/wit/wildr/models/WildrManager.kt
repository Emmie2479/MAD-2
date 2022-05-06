package ie.wit.wildr.models

import androidx.lifecycle.MutableLiveData
import ie.wit.wildr.api.WildrClient
import ie.wit.wildr.api.WildrWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

object WildrManager : WildrStore {

    private var donations = ArrayList<WildrModel>()

    override fun findAll(donationsList: MutableLiveData<List<WildrModel>>) {

        val call = WildrClient.getApi().getall()

        call.enqueue(object : Callback<List<WildrModel>> {
            override fun onResponse(call: Call<List<WildrModel>>,
                                    response: Response<List<WildrModel>>
            ) {
                donationsList.value = response.body() as ArrayList<WildrModel>
                Timber.i("Retrofit JSON = ${response.body()}")
            }

            override fun onFailure(call: Call<List<WildrModel>>, t: Throwable) {
                Timber.i("Retrofit Error : $t.message")
            }
        })
    }

    override fun findById(id:String) : WildrModel? {
        val foundAnimal: WildrModel? = donations.find { it._id == id }
        return foundAnimal
    }

    override fun create(animal: WildrModel) {

        val call = WildrClient.getApi().post(animal)

        call.enqueue(object : Callback<WildrWrapper> {
            override fun onResponse(call: Call<WildrWrapper>,
                                    response: Response<WildrWrapper>
            ) {
                val wildrWrapper = response.body()
                if (wildrWrapper != null) {
                    Timber.i("Retrofit ${wildrWrapper.message}")
                    Timber.i("Retrofit ${wildrWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<WildrWrapper>, t: Throwable) {
                        Timber.i("Retrofit Error : $t.message")
            }
        })
    }

    override fun delete(id: String) {
        val call = WildrClient.getApi().delete(id)

        call.enqueue(object : Callback<WildrWrapper> {
            override fun onResponse(call: Call<WildrWrapper>,
                                    response: Response<WildrWrapper>
            ) {
                val wildrWrapper = response.body()
                if (wildrWrapper != null) {
                    Timber.i("Retrofit Delete ${wildrWrapper.message}")
                    Timber.i("Retrofit Delete ${wildrWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<WildrWrapper>, t: Throwable) {
                Timber.i("Retrofit Delete Error : $t.message")
            }
        })
    }
}