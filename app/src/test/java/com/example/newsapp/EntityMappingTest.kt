package com.example.newsapp

import com.example.newsapp.data.entity.ArticleDbo
import com.example.newsapp.data.entity.ArticleDto
import com.example.newsapp.data.entity.SourceDto
import com.example.newsapp.data.entity.toArticleDbo
import org.junit.Test
import com.google.common.truth.Truth.assertThat

class EntityMappingTest {
    @Test
    fun `check map article dto to article dbo`() {
        val actual = ArticleDto(
            source = SourceDto("source name"),
            author = "author name",
            title = "article title",
            description = "some description",
            url = "url to news article",
            urlToImage = "url to image",
            publishedAt = "date 22.22.2222"
        )
        val expected = ArticleDbo(
            source = "source name",
            author = "author name",
            title = "article title",
            description = "some description",
            url = "url to news article",
            urlToImage = "url to image",
            publishedAt = "date 22.22.2222"
        )
        assertThat(expected).isEqualTo(actual.toArticleDbo())
    }
}