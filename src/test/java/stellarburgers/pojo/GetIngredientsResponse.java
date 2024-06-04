package stellarburgers.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetIngredientsResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<Ingredient> data;

    public GetIngredientsResponse() {
    }

    public GetIngredientsResponse(boolean success, List<Ingredient> data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Ingredient> getData() {
        return data;
    }

    public void setData(List<Ingredient> data) {
        this.data = data;
    }
}