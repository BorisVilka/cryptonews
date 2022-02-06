package org.cryptonews.main.ui.list_utils.data_sources;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import org.cryptonews.main.network.API;
import org.cryptonews.main.network.Answer;
import org.cryptonews.main.network.Coin;
import org.cryptonews.main.network.Info;
import org.cryptonews.main.network.Metadata;
import org.cryptonews.main.MyApp;
import org.cryptonews.main.ui.list_utils.ListItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;

public class PagedDataSource extends PositionalDataSource<ListItem> {

    private List<Coin> coins;
    private Map<String, Info> info;
    private List<ListItem> ans;

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<ListItem> callback) {

        Retrofit retrofit = MyApp.getClient().getRetrofitInstance();
        API api = retrofit.create(API.class);
        Call<Answer> call = api.getWallets(params.requestedLoadSize,Math.max(params.requestedStartPosition,1),MyApp.getUtils().getSortType(),MyApp.getUtils().getSortOrder());
        try {
           coins = call.execute().body().getCoins();
           StringBuilder query = new StringBuilder(coins.get(0).getId()+"");
           for(int i = 1;i<coins.size();i++) query.append(","+coins.get(i).getId());
           Call<Metadata> metadata = api.getMetadata(query.toString());
           info = metadata.execute().body().getInfo();
           ans = new ArrayList<>();
           for(Coin i:coins) ans.add(new ListItem(i,info.get(String.valueOf(i.getId()))));
           callback.onResult(ans,params.requestedStartPosition);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<ListItem> callback) {
        Retrofit retrofit = MyApp.getClient().getRetrofitInstance();
        API api = retrofit.create(API.class);
        Call<Answer> call = api.getWallets(params.loadSize,params.startPosition+1,MyApp.getUtils().getSortType(),MyApp.getUtils().getSortOrder());
        try {
            coins = call.execute().body().getCoins();
            StringBuilder query = new StringBuilder(coins.get(0).getId()+"");
            for(int i = 1;i<coins.size();i++) query.append(","+coins.get(i).getId());
            Call<Metadata> metadata = api.getMetadata(query.toString());
            info = metadata.execute().body().getInfo();
            ans = new ArrayList<>();
            for(Coin i:coins) ans.add(new ListItem(i,info.get(String.valueOf(i.getId()))));
            callback.onResult(ans);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
