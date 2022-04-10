package wgp.task2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import wgp.task2.Callbacks.TouchCallback;
import wgp.task2.Dialog.FileProgressDialog;
import wgp.task2.Dialog.InputDialog;
import wgp.task2.data.LinkData;
import wgp.task2.db.RemoteDataBase;
import wgp.task2.fragment.ComboKeyFragment;
import wgp.task2.fragment.DownloadListFragment;
import wgp.task2.fragment.FileManagerFragment;
import wgp.task2.fragment.HomeFragment;
import wgp.task2.fragment.HotKeyFragment;
import wgp.task2.fragment.LocalfileManagerFragment;
import wgp.task2.fragment.SingleHotKeyFragment;
import wgp.task2.fragment.TouchPadFragment;
import wgp.task2.fragment.UploadListFragment;
import wgp.task2.fragment.UsualLinkFragment;
import wgp.task2.socket.CmdClientSocket;
import wgp.task2.socket.FileDownLoadSocketThread;
import wgp.task2.utils.PermissionUtils;
import wgp.task2.view.LinkDataAdapter;

public class MainActivity extends AppCompatActivity implements InputDialog.Callback,
        LinkDataAdapter.ClickCallBack, TouchCallback.OnItemTouchCallbackListener,
        FileDownLoadSocketThread.DoneCallback, FileProgressDialog.OnDialogSubmitListener {
    NavigationView nav;
    DrawerLayout drawerLayout;
    FragmentManager fragmentManager;
    Fragment fileManagerFragment;
    Fragment localfileManagerFragment;
    Fragment touchPadFragment;
    Fragment homeFragment;
    Fragment usualLinkFragment;
    Fragment hotKeyFragment;
    Fragment comboKeyFragment;
    Fragment downloadListFragment;
    Fragment uploadListFragment;
    public static CmdClientSocket cmdClientSocket;
    public static List<Fragment> fragmentList = new ArrayList<Fragment>();
    RemoteDataBase remoteDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        drawerLayout = findViewById(R.id.drawer);
        remoteDataBase = new RemoteDataBase(this);
        PermissionUtils.requestPermission(this, PermissionUtils.WRITE_EXTERNAL_STORAGE);
        PermissionUtils.requestPermission(this, PermissionUtils.READ_EXTERNAL_STORAGE);
        nav = findViewById(R.id.navigation_view);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.common_connections:
                        System.out.println("您点击了常用连接");
                        showUsualLinkFragment();
                        break;
                    case R.id.file_manager:
                        System.out.println("您点击了文件管理器");
                        showFileManagerFragment();
                        break;
                    case R.id.local_file_manager:
                        System.out.println("您点击了本地文件");
                        showLocalFileManagerFragment();
                        break;
                    case R.id.analog_touchpad:
                        System.out.println("您点击了模拟触摸板");
                        showTouchPadFragment();
                        break;
                    case R.id.hotkey_menu:
                        System.out.println("您点击了热键菜单");
                        showHotKeyFragment();
                        break;
                    case R.id.combokey_menu:
                        System.out.println("您点击了组合键菜单");
                        showComboKeyFragment();
                        break;
                    case R.id.downloadList:
                        System.out.println("您点击了下载列表");
                        showDownloadListFragment();
                        break;
                    case R.id.uploadList:
                        System.out.println("您点击了上传列表");
                        showUploadListFragment();
                        break;
                }
                drawerLayout.closeDrawer(nav);
                return false;
            }
        });
        showHomeFragment();
//        if (android.os.Build.VERSION.SDK_INT > 9) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }
    }

    private void showLocalFileManagerFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (localfileManagerFragment == null) {
            localfileManagerFragment = new LocalfileManagerFragment();
            Bundle bundle = new Bundle();
//            bundle.putString("weather_id", gloableWeatherId.weatherId);
            localfileManagerFragment.setArguments(bundle);
            fragmentList.add(localfileManagerFragment);
            transaction.add(R.id.fragmentContainer, localfileManagerFragment);
        }
//        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        hideAllFragments(transaction);
        transaction.show(localfileManagerFragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opt_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opt_showNav:
                if (nav.isShown()) {
                    drawerLayout.closeDrawer(nav);
                } else {
                    drawerLayout.openDrawer(nav);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    // region ShowFragments
    public void showHomeFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            fragmentList.add(homeFragment);
            transaction.add(R.id.fragmentContainer, homeFragment);
        }
        hideAllFragments(transaction);
        transaction.show(homeFragment);
        transaction.commit();
    }
    public void showHotKeyFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (hotKeyFragment == null) {
            hotKeyFragment = new HotKeyFragment();
            fragmentList.add(hotKeyFragment);
            transaction.add(R.id.fragmentContainer, hotKeyFragment);
        }
        hideAllFragments(transaction);
        transaction.show(hotKeyFragment);
        transaction.commit();
    }
    public void showComboKeyFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (comboKeyFragment == null) {
            comboKeyFragment = new ComboKeyFragment();
            fragmentList.add(comboKeyFragment);
            transaction.add(R.id.fragmentContainer, comboKeyFragment);
        }
        hideAllFragments(transaction);
        transaction.show(comboKeyFragment);
        transaction.commit();
    }
    public void showDownloadListFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (downloadListFragment == null) {
            downloadListFragment = new DownloadListFragment();
            fragmentList.add(downloadListFragment);
            transaction.add(R.id.fragmentContainer, downloadListFragment);
        }
        hideAllFragments(transaction);
        transaction.show(downloadListFragment);
        transaction.commit();
    }
    public void showUploadListFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (uploadListFragment == null) {
            uploadListFragment = new UploadListFragment();
            fragmentList.add(uploadListFragment);
            transaction.add(R.id.fragmentContainer, uploadListFragment);
        }
        hideAllFragments(transaction);
        transaction.show(uploadListFragment);
        transaction.commit();
    }
    public void showTouchPadFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (touchPadFragment == null) {
            touchPadFragment = new TouchPadFragment();
            Bundle bundle = new Bundle();
//            bundle.putString("weather_id", gloableWeatherId.weatherId);
            touchPadFragment.setArguments(bundle);
            fragmentList.add(touchPadFragment);
            transaction.add(R.id.fragmentContainer, touchPadFragment);
        }
//        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        hideAllFragments(transaction);
        transaction.show(touchPadFragment);
        transaction.commit();
    }

    public void showFileManagerFragment() { //文件管理器
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fileManagerFragment == null) {
            fileManagerFragment = new FileManagerFragment();
            Bundle bundle = new Bundle();
//            bundle.putString("weather_id", gloableWeatherId.weatherId);
            fileManagerFragment.setArguments(bundle);
            fragmentList.add(fileManagerFragment);
            transaction.add(R.id.fragmentContainer, fileManagerFragment);
        }
//        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        hideAllFragments(transaction);
        transaction.show(fileManagerFragment);
        transaction.commit();
    }
    public void showFileManagerFragment(String link_ip, int link_port) { //文件管理器
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fileManagerFragment == null) {
            FileManagerFragment.link_ip = link_ip;
            FileManagerFragment.link_port = link_port;
            fileManagerFragment = new FileManagerFragment();
            Bundle bundle = new Bundle();
//            bundle.putString("weather_id", gloableWeatherId.weatherId);
            fileManagerFragment.setArguments(bundle);
            fragmentList.add(fileManagerFragment);
            transaction.add(R.id.fragmentContainer, fileManagerFragment);
        }
//        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        hideAllFragments(transaction);
        transaction.show(fileManagerFragment);
        transaction.commit();
    }
    public void showUsualLinkFragment() { // 常用连接
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (usualLinkFragment == null) {
            usualLinkFragment = new UsualLinkFragment();
            Bundle bundle = new Bundle();
//            bundle.putString("weather_id", gloableWeatherId.weatherId);
            usualLinkFragment.setArguments(bundle);
            fragmentList.add(usualLinkFragment);
            transaction.add(R.id.fragmentContainer, usualLinkFragment);
        }
//        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        hideAllFragments(transaction);
        transaction.show(usualLinkFragment);
        transaction.commit();
    }
    public void hideAllFragments(FragmentTransaction transaction) {
        for (Fragment frag : fragmentList) {
            if (frag != null) {
                transaction.hide(frag);
            }
        }
    }
    // endregion
    // region 实现的接口
    @Override
    public void onCpsClick(String cpsString) {

    }

    @Override
    public void onDefaultClick(String str) {
        remoteDataBase.open();
        remoteDataBase.insertData(new LinkData(str, FileManagerFragment.link_ip, FileManagerFragment.link_port));
    }

    @Override
    public void onWebClick(String cpsString) {

    }

    @Override
    public void onUsualLinkClick(LinkData linkData) {
        fragmentList.remove(fileManagerFragment);
        fileManagerFragment = null;
        showFileManagerFragment(linkData.getLinkIp(), linkData.getLinkPort());
    }

    @Override
    public void onSwiped(int adapterPosition) {

    }

    @Override
    public void onDoneCallback(int port) {
//        remoteDataBase.insertData();
//        String cmd = "clo:" + port;
//        cmdClientSocket.work(cmd);
    }

    @Override
    public boolean onMove(int srcPosition, int targetPosition) {
        return false;
    }

    @Override
    public void onSubmit(long CounterSize) {
        showToast("实际下载：" + CounterSize);
        remoteDataBase.updateFileData(); // 修改或者插入数据
    }

    // endregion
    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}