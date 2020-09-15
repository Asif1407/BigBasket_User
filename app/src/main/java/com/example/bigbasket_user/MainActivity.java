package com.example.bigbasket_user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.media.tv.TvView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import Fragments.AccountFragment;
import Fragments.CategoryFragment;
import Fragments.HistoryFragment;
import Fragments.HomeFragment;
import Fragments.OfferFragment;
import Fragments.SearchFragment;
import NavFragments.AboutUsFragment;
import NavFragments.ContactUsFragment;
import NavFragments.ReviewFragment;
import NavFragments.TermsFragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView btm_navigation;
    private Fragment selectorFragment;
    private Toolbar toolbar;

    FirebaseAuth mAuth;

    // Drawer Layout and navigation Slide.
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        btm_navigation = findViewById(R.id.btm_navigation);
        loadFragment(new HomeFragment());
        navigationView = findViewById(R.id.navSlideBar);
        View headerView = navigationView.getHeaderView(0);
        CircleImageView image = headerView.findViewById(R.id.imageView);
        TextView name = headerView.findViewById(R.id.name);
        TextView email = headerView.findViewById(R.id.email);

        gettingUserData(image,name,email);

         mAuth = FirebaseAuth.getInstance();
        checkUser();

        setupNavSlide();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.home:
                        selectorFragment = new HomeFragment();
                        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.category:
                        selectorFragment = new CategoryFragment();
                        Toast.makeText(MainActivity.this, "Category", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.history:
                        selectorFragment = new HistoryFragment();
                        Toast.makeText(MainActivity.this, "History", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.review:
                        selectorFragment = new ReviewFragment();
                        Toast.makeText(MainActivity.this, "Review", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.logOut:
                        Toast.makeText(MainActivity.this, "Logging Out", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        FirebaseAuth.getInstance().signOut();
                        movetoSigninActivity();
                        break;

                    case R.id.tnc:
                        selectorFragment = new TermsFragment();
                        Toast.makeText(MainActivity.this, "Terms And Condition", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.aboutUs:
                        selectorFragment = new AboutUsFragment();
                        Toast.makeText(MainActivity.this, "About Us", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.contactUs:
                        selectorFragment = new ContactUsFragment();
                        Toast.makeText(MainActivity.this, "Contact Us", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                }

                if (selectorFragment != null) {
                    //Replacing the fragments accordingly.
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectorFragment).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                }

                return true;
            }
        });

        btm_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.home_btm:
                        selectorFragment = new HomeFragment();
                        break;

                    case R.id.categories_btm:
                        selectorFragment = new CategoryFragment();
                        break;

                    case R.id.offers_btm:
                        selectorFragment = new OfferFragment();
                        break;

                    case R.id.accounts_btm:
                        selectorFragment = new AccountFragment();
                        break;

                    case R.id.history_btm:
                        selectorFragment = new SearchFragment();
                        break;
                }

                if (selectorFragment != null) {
                    //Replacing the fragments accordingly.
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectorFragment).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                }
                return true;
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.cartBtn:
                        startActivity(new Intent(MainActivity.this, CartActivity.class));
                }
                return true;
            }
        });

    }

    private void gettingUserData(final CircleImageView image, final TextView name, final TextView email) {

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String Uid = currentUser.getUid();

        FirebaseFirestore.getInstance().collection("Users").document(Uid)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        name.setText(value.getString("name"));
                        email.setText(value.getString("address"));
                        try {
                            if (!value.getString("profileImage").isEmpty()) {
                                Picasso.get().load(value.getString("profileImage")).placeholder(R.drawable.ic_account).into(image);
                            } else {
                                Picasso.get().load(R.drawable.ic_account).placeholder(R.drawable.ic_account).into(image);
                            }
                        } catch (Exception z) {
                            Picasso.get().load(R.drawable.ic_account).placeholder(R.drawable.ic_account).into(image);
                        }
                    }
                });
    }


    private void setupNavSlide() {
        drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        // This Sync State handle all the function of Navigation BAr by overriding the methods.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment!=null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }
        return false;
    }

    private void checkUser() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
            finish();
        }
        else {
        }
    }

    public void movetoSigninActivity(){

        Intent intent = new Intent(MainActivity.this,SignInActivity.class);
        startActivity(intent);
        finish();
    }
}
