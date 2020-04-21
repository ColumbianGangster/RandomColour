package com.heroku.myapp.randomcolour.data

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import retrofit2.Response

lateinit var SUT: RandomWordsRepository

@RunWith(Enclosed::class)
class RandomWordsRepositoryTest {
    class GetInitial {
        @Test
        fun `on success content is returned`() {
            // given
            val resp: List<String> = emptyList()
            val api: RandomWordApi = mock {
                on { it.getRandomWords(any()) } doReturn Single.just(Response.success(resp))
            }
            val dataSource: RandomWordsDataSource = mock()
            SUT = RandomWordsRepository(api, dataSource)

            // when
            val testObserver = SUT.getRandomWords(5).test()

            // then
            testObserver.assertNoErrors()
            testObserver.assertValue { it == resp }
            testObserver.assertComplete()
        }

        @Test
        fun `on failure error returned`() {
            // TODO
        }
    }

    class GetManualRefresh {
        @Test
        fun `on success content is returned`() {
            // given
            val resp: List<String> = emptyList()
            val api: RandomWordApi = mock {
                on { it.getRandomWords(any()) } doReturn Single.just(Response.success(resp))
            }
            val dataSource: RandomWordsDataSource = mock()
            SUT = RandomWordsRepository(api, dataSource)

            // when
            val testObserver = SUT.getRandomWordsManualRefresh(5).test()

            // then
            testObserver.assertNoErrors()
            testObserver.assertValue { it == resp }
            testObserver.assertComplete()
        }

        @Test
        fun `on failure error returned`() {
            // TODO
        }
    }

}