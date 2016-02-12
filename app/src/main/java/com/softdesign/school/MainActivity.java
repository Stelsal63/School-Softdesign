package com.softdesign.school;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.softdesign.school.ui.fragments.ContactsFragment;
import com.softdesign.school.ui.fragments.ProfileFragment;
import com.softdesign.school.ui.fragments.SettingFragment;
import com.softdesign.school.ui.fragments.TasksFragment;
import com.softdesign.school.ui.fragments.TeamFragment;
import com.softdesign.school.utils.Lg;

public class MainActivity extends AppCompatActivity {

    public static final String CHECKED_KEY = "checked";
    private static final String FRAGMENT_TAG = "fragment";

    private NavigationView mNavigationView;
    private DrawerLayout mNavigationDrawer;
    private Fragment mFragment;
    private FrameLayout mFrameContainer;
    private MenuItem mItem;
    private ImageView mImageView;
    private View mHeaderView;

    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Lg.e(this.getLocalClassName(), "=============================");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("School Lesson 3");

        mNavigationDrawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        mHeaderView = mNavigationView.inflateHeaderView(R.layout.navigation_header);
        mImageView = (ImageView) mHeaderView.findViewById(R.id.drawerAvatar);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.profile_icon);
        mImageView.setImageBitmap(getCircleBitmap(bm));

        Lg.e(this.getLocalClassName(), "on create");


        setupDrawer();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setupToolbar();

        if (savedInstanceState == null) {
            mItem = mNavigationView.getMenu().findItem(R.id.drawer_profile);
            mItem.setChecked(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_container, new ProfileFragment(), FRAGMENT_TAG).commit();
        }
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mNavigationDrawer.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Lg.e(this.getLocalClassName(), "on start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Lg.e(this.getLocalClassName(), "on resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Lg.e(this.getLocalClassName(), "on pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Lg.e(this.getLocalClassName(), "on stop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Lg.e(this.getLocalClassName(), "on restart");
    }

    private void setupDrawer() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.drawer_profile:
                        mFragment = new ProfileFragment();
                        break;
                    case R.id.drawer_contacts:
                        mFragment = new ContactsFragment();
                        break;
                    case R.id.drawer_setting:
                        mFragment = new SettingFragment();
                        break;
                    case R.id.drawer_tasks:
                        mFragment = new TasksFragment();
                        break;
                    case R.id.drawer_team:
                        mFragment = new TeamFragment();
                        break;
                }

                itemChecked(item);

                if (mFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_container, mFragment, FRAGMENT_TAG).addToBackStack(null).commit();
                }

                mNavigationDrawer.closeDrawers();
                return false;
            }

        });
    }

    private void itemChecked(MenuItem item) {
        mItem.setChecked(false);
        item.setChecked(true);
        mItem = item;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Lg.e(this.getLocalClassName(), "on destroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CHECKED_KEY, mItem.getItemId());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mItem = mNavigationView.getMenu().findItem(savedInstanceState.getInt(CHECKED_KEY));
        mItem.setChecked(true);

    }

    @Override
    public void onBackPressed() {


        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
            System.exit(0);
        }

        super.onBackPressed();
        Fragment backFragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);


        if (backFragment instanceof ProfileFragment) {
            itemChecked(mNavigationView.getMenu().findItem(R.id.drawer_profile));
        }

        if (backFragment instanceof ContactsFragment) {
            itemChecked(mNavigationView.getMenu().findItem(R.id.drawer_contacts));
        }

        if (backFragment instanceof TeamFragment) {
            itemChecked(mNavigationView.getMenu().findItem(R.id.drawer_team));
        }

        if (backFragment instanceof TasksFragment) {
            itemChecked(mNavigationView.getMenu().findItem(R.id.drawer_tasks));
        }

        if (backFragment instanceof SettingFragment) {
            itemChecked(mNavigationView.getMenu().findItem(R.id.drawer_setting));
        }
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setShadowLayer(5.5f, 6.0f, 6.0f, Color.BLACK);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }

}
