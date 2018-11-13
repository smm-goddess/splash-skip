package mm.dd.tools.accessibility.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface SkipDao {

    @Query("SELECT * FROM package_info")
    fun getAll(): LiveData<List<SkipEntity>>

    @Query(value = "SELECT * FROM PACKAGE_INFO WHERE enable=1")
    fun getAllUsableRules(): LiveData<List<SkipEntity>>

    @Insert
    fun insertNewRule(skipEntity: SkipEntity)
}