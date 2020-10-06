import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VerificationData(
    @SerializedName("image_url") var imageUrl: String= ""
)  : Parcelable