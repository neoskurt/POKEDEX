package com.requests

import okhttp3.{OkHttpClient, Request}


object ClientApi {
  def run(url: String): String = {
    val client = new OkHttpClient
    val request = new Request.Builder().url(url).build
    try {
      val response = client.newCall(request).execute
      try response.body.string
      finally if (response != null) response.close()
    }
  }
}
