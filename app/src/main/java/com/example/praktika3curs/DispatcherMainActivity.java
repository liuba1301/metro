package com.example.praktika3curs;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DispatcherMainActivity extends AppCompatActivity {
    private static final String[] TAB_TITLES = {"Заявки", "Сотрудники", "Расписание"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatcher_mode);

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        viewPager.setAdapter(new DispatcherPagerAdapter(this));

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(TAB_TITLES[position])
        ).attach();
    }

    private static class DispatcherPagerAdapter extends FragmentStateAdapter {
        public DispatcherPagerAdapter(@NonNull FragmentActivity fa) {
            super(fa);
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0: return new ApplicationsFragment();
                case 1: return new EmployeesFragment();
                case 2: return new ScheduleFragment();
                default: return new ApplicationsFragment();
            }
        }
        @Override
        public int getItemCount() {
            return 3;
        }
    }
} 