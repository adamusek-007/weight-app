package pl.kuczabinski.weights.weight

//class WeightRepository(
//    private val localDao: WeightDao,
////    private val remoteApi: WeightApi,
////    private val syncManager: SyncManager
//) {
//
//    fun getWeightEntries(): List<DBEntry> {
//        return localDao.getAll()
//    }
//
//    fun addWeightEntry(weight: Float) {
//        val entry = DBEntry(
//            weight = weight
//        )
//        localDao.insert(entry)
////        syncManager.scheduleSyncForEntry(entry.id)
//    }
//
//    // Usuwa lokalnie i planuje sync
//    fun deleteWeightEntry(id: String) {
//        localDao.delete(id)
////        syncManager.scheduleSyncForEntry(id)
//    }
//}