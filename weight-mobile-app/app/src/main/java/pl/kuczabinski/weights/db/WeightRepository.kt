package pl.kuczabinski.weights.db

import kotlinx.coroutines.flow.Flow

class WeightRepository(
    private val localDao: WeightDao,
//    private val remoteApi: WeightApi,
//    private val syncManager: SyncManager
) {

    suspend fun getWeightEntries(): List<WeightEntry> {
        return localDao.getAll()
    }

    suspend fun addWeightEntry(weight: Double) {
        val entry = WeightEntry(
            weight = weight
        )
        localDao.insert(entry)
//        syncManager.scheduleSyncForEntry(entry.id)
    }

    // Usuwa lokalnie i planuje sync
    suspend fun deleteWeightEntry(id: String) {
        localDao.delete(id)
//        syncManager.scheduleSyncForEntry(id)
    }
}