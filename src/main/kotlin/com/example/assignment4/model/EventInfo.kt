package com.example.assignment4.model

import com.example.assignment4.entity.Event

data class EventInfo(val event: Event, val isAttending: Boolean, val isLiked: Boolean, val isHost: Boolean)