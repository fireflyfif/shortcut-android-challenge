package com.example.android.myapplication.repository;

import android.arch.lifecycle.LiveData;

import com.example.android.myapplication.data.XkcdService;
import com.example.android.myapplication.model.CurrentXkcdComic;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class XkcdRepositoryTest {

    private XkcdRepository repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        repository = Mockito.spy(new XkcdRepository());
    }

    /*
    Test for the Repository class if it returns LiveData when the network call is made
    Help from the following repo: https://github.com/ericntd/Github-Search
     */
    @Test
    public void getCurrentComics_MadeNetworkCall_ShouldVerifyTheCall() {

        LiveData comicsData = Mockito.mock(LiveData.class);
        Mockito.doReturn(comicsData).when(repository).getCurrentComics();

        // Trigger
        repository.getCurrentComics();
        // Validation
        Mockito.verify(repository, Mockito.times(1)).getCurrentComics();
    }

    /*
    Test the API response if it returns LiveData when the network call is made
    Help the SO post: https://stackoverflow.com/a/45166524/8132331
     */
    @SuppressWarnings("unckecked")
    @Test
    public void testApiResponse_AfterMakingNetworkCall_ShouldReturnCallback() {

        XkcdService mockedService = Mockito.mock(XkcdService.class);
        final Call<CurrentXkcdComic> mockedCall = Mockito.mock(Call.class);

        Mockito.when(mockedService.getCurrentComic()).thenReturn(mockedCall);

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Callback<CurrentXkcdComic> callback = invocation.getArgument(0);
                callback.onResponse(mockedCall, Response.success(new CurrentXkcdComic()));

                return null;
            }
        }).when(mockedCall).enqueue(any(Callback.class));
    }

}