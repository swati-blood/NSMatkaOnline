package in.matka.NS.Remote;



import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IUpoladAPI {
    @Multipart
    @POST("api.php?api=upload_image")
    Call<String> uploadFile(@Part MultipartBody.Part file);
}
