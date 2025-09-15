package pl.kuczabinski.weights.db

import kotlinx.coroutines.flow.Flow

class WeightRepository(
    private val localDao: WeightDao,
//    private val remoteApi: WeightApi,
//    private val syncManager: SyncManager
) {

    fun getWeightEntries(): List<WeightEntry> {
        return localDao.getAll()
    }

    fun addWeightEntry(weight: Float) {
        val entry = WeightEntry(
            weight = weight
        )
        localDao.insert(entry)
//        syncManager.scheduleSyncForEntry(entry.id)
    }

    // Usuwa lokalnie i planuje sync
    fun deleteWeightEntry(id: String) {
        localDao.delete(id)
//        syncManager.scheduleSyncForEntry(id)
    }
}