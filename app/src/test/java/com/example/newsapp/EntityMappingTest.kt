package com.example.newsapp

import com.example.newsapp.data.entity.ArticleDbo
import com.example.newsapp.data.entity.ArticleDto
import com.example.newsapp.data.entity.SourceDto
import com.example.newsapp.data.entity.toArticleDbo
import com.example.newsapp.data.entity.toArticleDto
import com.example.newsapp.domain.entity.Article
import com.example.newsapp.domain.entity.Source
import com.example.newsapp.domain.entity.toArticle
import com.example.newsapp.domain.entity.toArticleDto
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import org.junit.Before

class EntityMappingTest {

    private lateinit var articleDbo: ArticleDbo
    private lateinit var articleDto: ArticleDto
    private lateinit var article: Article

    @Before
    fun startEntityTest() {
        articleDbo = ArticleDbo(
            source = "source name",
            author = "author name",
            title = "article title",
            description = "some description",
            url = "url to news article",
            urlToImage = "url to image",
            publishedAt = "date 22.22.2222"
        )
        articleDto = ArticleDto(
            source = SourceDto("source name"),
            author = "author name",
            title = "article title",
            description = "some description",
            url = "url to news article",
            urlToImage = "url to image",
            publishedAt = "date 22.22.2222"
        )
        article = Article(
            source = Source("source name"),
            author = "author name",
            title = "article title",
            description = "some description",
            url = "url to news article",
            urlToImage = "url to image",
            publishedAt = "date 22.22.2222"
        )
    }

    @Test
    fun `check map article dto to article dbo`() {
        val actual = articleDto
        val expected = articleDbo
        assertThat(expected).isEqualTo(actual.toArticleDbo())
    }

    @Test
    fun `check map article dbo to article dto`() {
        val actual = articleDbo
        val expected = articleDto

        assertThat(expected).isEqualTo(actual.toArticleDto())
    }

    @Test
    fun `check map article dto to article`() {
        val actual = articleDto
        val expected = article

        assertThat(expected).isEqualTo(actual.toArticle())
    }

    @Test
    fun `check map article to article dto`() {
        val actual = article
        val expected = articleDto

        assertThat(expected).isEqualTo(actual.toArticleDto())
    }
}