package pjh.mjc.witness;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;

import androidx.annotation.Nullable;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

@SuppressWarnings("deprecation")
public class FindId extends TabActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_id);

        TabHost tabHost = getTabHost();
        TabHost.TabSpec tabSpecId = tabHost.newTabSpec("ID").setIndicator("아이디 찾기");
        tabSpecId.setContent(R.id.tabfindId);
        tabHost.addTab(tabSpecId);

        TabHost.TabSpec tabSpecPw = tabHost.newTabSpec("PW").setIndicator("비밀번호 찾기");
        tabSpecPw.setContent(R.id.tabfindPw);
        tabHost.addTab(tabSpecPw);

        tabHost.setCurrentTab(0);

    }
}
