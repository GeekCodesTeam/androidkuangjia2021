package com.kunminx.linkagelistview;

/*
 * Copyright (c) 2018-present. KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * Create by KunMinX at 19/4/27
 */
public class ElmAct extends AppCompatActivity {

    private String[] mFragmentTitles;
    private String[] mFragmentPaths;
    private Fragment[] mFragments;
    private Toolbar toolbar;
    private TabLayout tabs;
    private ViewPager2 view_pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainelm);
        toolbar =findViewById(R.id.toolbar);
        tabs =findViewById(R.id.tabs);
        view_pager =findViewById(R.id.view_pager);
        toolbar.setTitle(R.string.app_nameliblinkagerecyclerview);
        setSupportActionBar(toolbar);

        mFragmentTitles = getResources().getStringArray(R.array.fragments);
        mFragmentPaths = getResources().getStringArray(R.array.fragments_full_path);
        mFragments = new Fragment[mFragmentTitles.length];

        view_pager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return ElmAct.this.createFragment(position);
            }

            @Override
            public int getItemCount() {
                return mFragmentTitles.length;
            }
        });

        new TabLayoutMediator(tabs, view_pager, (tab, position) -> {
            tab.setText(mFragmentTitles[position]
                    .replace("SampleFragment", "")
                    .replaceAll("[A-Z]", " $0"));
        }).attach();
    }

    private Fragment createFragment(Integer index) {
        if (mFragments[index] != null) {
            return mFragments[index];
        }
        String name = mFragmentPaths[index];
        Fragment fragment = null;
        try {
            fragment = (Fragment) Class.forName(name).newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        mFragments[index] = fragment;
        return mFragments[index];
    }
}
