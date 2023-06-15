package com.example.fastcampus.part2.ch14_chat.chatdetail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fastcampus.DB_CHATS
import com.example.fastcampus.DB_CHAT_ROOMS
import com.example.fastcampus.DB_USERS
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityChatDetailBinding
import com.example.fastcampus.part2.ch14_chat.userlist.UserItem
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatDetailActivity : AppCompatActivity() {

    companion object {
        const val OTHER_USER_ID = "otherUserId"
        const val CHAT_ROOM_ID = "chatRoomId"
    }

    private lateinit var binding: ActivityChatDetailBinding

    private var chatRoomId: String = ""
    private var otherUserId: String = ""
    private var myUserId: String = ""
    private var myUserName: String = ""

    private val chatItemList = mutableListOf<ChatItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatRoomId = intent.getStringExtra(CHAT_ROOM_ID) ?: return
        otherUserId = intent.getStringExtra(OTHER_USER_ID) ?: return
        myUserId = Firebase.auth.currentUser?.uid ?: ""

        val chatAdapter = ChatAdapter()

        Firebase.database.reference.child(DB_USERS).child(myUserId).get()
            .addOnSuccessListener {
                val myUserItem = it.getValue(UserItem::class.java)
                myUserName = myUserItem?.username ?: ""
            }

        Firebase.database.reference.child(DB_USERS).child(otherUserId).get()
            .addOnSuccessListener {
                val otherUserItem = it.getValue(UserItem::class.java)
                chatAdapter.otherUserItem = otherUserItem
            }

        Firebase.database.reference.child(DB_CHATS).child(chatRoomId)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val chatItem = snapshot.getValue(ChatItem::class.java)
                    chatItem ?: return

                    chatItemList.add(chatItem)
                    chatAdapter.submitList(chatItemList.toMutableList())
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

                override fun onChildRemoved(snapshot: DataSnapshot) {}

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

                override fun onCancelled(error: DatabaseError) {}

            })


        with(binding) {
            chatRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = chatAdapter
            }

            sendButton.setOnClickListener {
                val message = messageEditText.text.toString()

                if (message.isEmpty()) {
                    Toast.makeText(
                        this@ChatDetailActivity,
                        getString(R.string.msg_input),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                val newChatItem = ChatItem(
                    message = message,
                    userId = myUserId
                )

                Firebase.database.reference.child(DB_CHATS).child(chatRoomId).push().apply {
                    newChatItem.chatId = key
                    setValue(newChatItem)
                }

                val updates: MutableMap<String, Any> = hashMapOf(
                    "${DB_CHAT_ROOMS}/$myUserId/$otherUserId/lastMessage" to message,
                    "${DB_CHAT_ROOMS}/$otherUserId/$myUserId/lastMessage" to message,
                    "${DB_CHAT_ROOMS}/$otherUserId/$myUserId/chatRoomId" to chatRoomId,
                    "${DB_CHAT_ROOMS}/$otherUserId/$myUserId/otherUserId" to myUserId,
                    "${DB_CHAT_ROOMS}/$otherUserId/$myUserId/otherUserName" to myUserName,
                )

                Firebase.database.reference.updateChildren(updates)

                messageEditText.text.clear()
            }
        }
    }
}