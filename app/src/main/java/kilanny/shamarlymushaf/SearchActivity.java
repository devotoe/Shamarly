package kilanny.shamarlymushaf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

public class SearchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final ListView results = (ListView) findViewById(R.id.listViewResults);
        results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchResult result = (SearchResult) results.getItemAtPosition(position);
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                intent.putExtra(MainActivity.SHOW_PAGE_MESSAGE, result.page);
                startActivity(intent);
            }
        });
        SearchView search = (SearchView) findViewById(R.id.searchTextView);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                DbManager db = DbManager.getInstance();
                SearchResult res[] = db.search(query)
                        .toArray(new SearchResult[0]);
                results.setAdapter(new ArrayAdapter<>(SearchActivity.this,
                        android.R.layout.simple_list_item_1, res));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}
