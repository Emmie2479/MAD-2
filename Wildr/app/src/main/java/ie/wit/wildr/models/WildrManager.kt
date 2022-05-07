package ie.wit.wildr.models

import androidx.lifecycle.MutableLiveData
import ie.wit.wildr.api.WildrClient
import ie.wit.wildr.api.WildrWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

object WildrManager : WildrStore {

    override fun findAll(animalsCatalogue: MutableLiveData<List<WildrModel>>) {

        val call = WildrClient.getApi().findall()

        call.enqueue(object : Callback<List<WildrModel>> {
            override fun onResponse(call: Call<List<WildrModel>>,
                                    response: Response<List<WildrModel>>
            ) {
                animalsCatalogue.value = response.body() as ArrayList<WildrModel>
                Timber.i("Retrofit findAll() = ${response.body()}")
            }

            override fun onFailure(call: Call<List<WildrModel>>, t: Throwable) {
                Timber.i("Retrofit findAll() Error : $t.message")
            }
        })
    }

    override fun findAll(email: String, animalsCatalogue: MutableLiveData<List<WildrModel>>) {

        val call = WildrClient.getApi().findall(email)

        call.enqueue(object : Callback<List<WildrModel>> {
            override fun onResponse(call: Call<List<WildrModel>>,
                                    response: Response<List<WildrModel>>
            ) {
                animalsCatalogue.value = response.body() as ArrayList<WildrModel>
                Timber.i("Retrofit findAll() = ${response.body()}")
            }

            override fun onFailure(call: Call<List<WildrModel>>, t: Throwable) {
                Timber.i("Retrofit findAll() Error : $t.message")
            }
        })
    }

    override fun findById(email: String, id: String, animal: MutableLiveData<WildrModel>)   {

        val call = WildrClient.getApi().get(email,id)

        call.enqueue(object : Callback<WildrModel> {
            override fun onResponse(call: Call<WildrModel>, response: Response<WildrModel>) {
                animal.value = response.body() as WildrModel
                Timber.i("Retrofit findById() = ${response.body()}")
            }

            override fun onFailure(call: Call<WildrModel>, t: Throwable) {
                Timber.i("Retrofit findById() Error : $t.message")
            }
        })
    }

    override fun create( animal: WildrModel) {

        val call = WildrClient.getApi().post(animal.email,animal)
        Timber.i("Retrofit ${call.toString()}")

        call.enqueue(object : Callback<WildrWrapper> {
            override fun onResponse(call: Call<WildrWrapper>,
                                    response: Response<WildrWrapper>
            ) {
                val animalWrapper = response.body()
                if (animalWrapper != null) {
                    Timber.i("Retrofit ${animalWrapper.message}")
                    Timber.i("Retrofit ${animalWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<WildrWrapper>, t: Throwable) {
                Timber.i("Retrofit Error : $t.message")
                Timber.i("Retrofit create Error : $t.message")
            }
        })
    }

    override fun delete(email: String,id: String) {

        val call = WildrClient.getApi().delete(email,id)

        call.enqueue(object : Callback<WildrWrapper> {
            override fun onResponse(call: Call<WildrWrapper>,
                                    response: Response<WildrWrapper>
            ) {
                val animalWrapper = response.body()
                if (animalWrapper != null) {
                    Timber.i("Retrofit Delete ${animalWrapper.message}")
                    Timber.i("Retrofit Delete ${animalWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<WildrWrapper>, t: Throwable) {
                Timber.i("Retrofit Delete Error : $t.message")
            }
        })
    }

    override fun update(email: String,id: String, animal: WildrModel) {

        val call = WildrClient.getApi().put(email,id,animal)

        call.enqueue(object : Callback<WildrWrapper> {
            override fun onResponse(call: Call<WildrWrapper>,
                                    response: Response<WildrWrapper>
            ) {
                val animalWrapper = response.body()
                if (animalWrapper != null) {
                    Timber.i("Retrofit Update ${animalWrapper.message}")
                    Timber.i("Retrofit Update ${animalWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<WildrWrapper>, t: Throwable) {
                Timber.i("Retrofit Update Error : $t.message")
            }
        })
    }
}