package com.example.androiddevchallenge.data

class FakePuppyRepository : Repository {

    override fun getAllPuppyData(): List<PuppyData> {
        return DataSource.data
    }

    override fun getPuppyData(id: String): PuppyData {
        return DataSource.data.first { it.id == id }
    }
}