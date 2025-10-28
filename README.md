# Cellular Usage Demo App

## Overview
This is a modern Android demo app built with **Jetpack Compose**, **MVVM**, **StateFlow**,**Unit Tests**, and **Hilt DI**.

It simulates a prepaid telecom experience:
- Shows your current mobile usage (data / minutes / SMS)
- Shows promotional offers
- Shows available recharge plans
- Lets you toggle plan renewal reminders via a settings screen

This project was designed to meet the exact requirements of the assignment:
- **Usage Dashboard**
- **Promotions / Ads**
- **Plans / Packages screen**
- **Settings + optional reminder notification**
- **MVVM + Mock data + Compose UI**
- **Unit tests for ViewModels**
- **Normal Mode and Dark Mode Implementation**

---

## App Features / Screens

<img width="1080" height="2400" alt="Screenshot_20251029_024252" src="https://github.com/user-attachments/assets/b1e56688-19ae-42cc-9648-d8860799d88f" />
<img width="1080" height="2400" alt="Screenshot_20251029_024300" src="https://github.com/user-attachments/assets/2e701dd0-318b-4489-8e76-1a9bfcc2550d" />
<img width="1080" height="2400" alt="Screenshot_20251029_024308" src="https://github.com/user-attachments/assets/7ffebc9d-4ca3-46ba-8f3c-648ad867dedf" />
<img width="1080" height="2400" alt="Screenshot_20251029_024201" src="https://github.com/user-attachments/assets/1a962aa5-fef5-4ee4-ba2f-f8afd5446583" />
<img width="1080" height="2400" alt="Screenshot_20251029_024219" src="https://github.com/user-attachments/assets/7787e700-5cf3-4cc2-ace9-44f94843dfac" />
<img width="1080" height="2400" alt="Screenshot_20251029_024229" src="https://github.com/user-attachments/assets/3bda7e27-d878-4848-8746-173286af473f" />


### 1. Dashboard Screen (`DashboardScreen`)
- Top header row:
  - Leading circular icon chip (analytics-style)
  - Title “Your Usage”
  - Subtitle: `Plan renews on <date>`
- **UsageCard**
  - Shows:
    - Data: e.g. `2.3 GB of 5 GB`
    - Minutes: e.g. `340 / 1000`
    - SMS: e.g. `120 / 500`
  - Each with modern visual progress indicators
  - Also shows current balance and renewal date
- **Promotions**
  - List of promotional banners powered by `Promo`
  - Each promo card uses a frosted “glass” look:
    - Translucent surface
    - Soft glow halo behind the card
    - Gentle floating/bobbing animation
  - Each promo has:
    - Title
    - Short description
    - CTA button like “Subscribe”, “Know More”, etc.
- **Available Plans (Preview)**
  - Top plans displayed inline using `PlanCard`
  - “See all” navigates to the full Plans screen
- Footer copyright text

Visual polish:
- Uses `statusBarsPadding()` + top padding so content doesn't clash with the system bar
- Typography and colors come from Material 3 with light/dark support

---

### 2. Plans Screen (`PlansScreen`)
- Section header: “📦 Available Plans”
- Vertical list of all plans from the repository, shown with `PlanCard`
- Each plan shows:
  - Name (e.g. `Super 299`)
  - Price (e.g. `₹299`)
  - Data allowance (e.g. `3GB/day`)
  - Minutes (e.g. `1000` or `Unlimited`)
  - SMS limit
  - A `Subscribe` button (mock only; no backend)
- Helper text at the bottom:
  > Tap "Subscribe" to simulate purchase. No backend calls are made.

This satisfies the assignment's "Packages / Plans Page" requirement.

---

### 3. Settings Screen (`SettingsScreen`)
- Large header: “⚙ Settings”
- Intro text explaining what the screen does
- A modern card block with:
  - Leading circular icon chip (bell / notification icon)
  - Title “Plan expiry reminder”
  - Subtitle: “Get an alert before renewal and when balance is low.”
  - A `Switch` to enable/disable reminder
- Below the toggle row:
  - A subtle divider and a status line, e.g.
    - “Status: Enabled • You'll receive renewal reminders.”
    - or “Status: Off • You won't get renewal alerts.”
- Additional info section:
  - Explains this is a local-only demo notification
  - Clarifies there's no backend or network call happening

When you turn on the reminder:
- The app can immediately post a local notification (using `NotificationHelper`)
- Notification content is like “Your plan renews on Nov 1, 2025”

This covers the "Bonus Points: Add local notification reminding user about low balance or expiring plan."

---

### 4. Bottom Navigation / App Shell (`MainActivity`)
- Single-Activity architecture using `NavHost` (Navigation Compose)
- Tabs:
  - Dashboard
  - Plans
  - Settings
- Custom bottom bar (`FancyBottomBar`) instead of default `NavigationBar`:
  - Floating glass / frosted dock with rounded corners
  - Soft shadow to look elevated
  - Selected tab:
    - Gets a pill highlight background
    - Icon scales up slightly with animation
    - Label fades in (“Home”, “Plans”, “Settings”)
- Navigation behavior:
  - Uses `popUpTo(findStartDestination())` + `launchSingleTop = true` + `restoreState = true`
  - This prevents stacking multiple copies of the same screen and restores tab state

We also handle padding and shadows so the bar appears like a floating dock above the system nav area, instead of a full-width solid bar stuck to the very bottom.

---

## Architecture

### High-level
- **Single-Activity app** (`MainActivity`)
- **Jetpack Compose UI**
- **Navigation Compose** for screen routing
- **MVVM** with `ViewModel` + `StateFlow`
- **Repository pattern** with in-memory mock data
- **Hilt DI** for injection

This matches the requirement: “Use MVVM pattern. Maintain clear separation between Model / ViewModel / View.”

### Layer breakdown

#### `data/model`
Data classes:
- `UsageStats`
  - `dataUsedGb`, `dataTotalGb`
  - `minutesUsed`, `minutesTotal`
  - `smsUsed`, `smsTotal`
  - `balance`
  - `renewalDate`
- `Promo`
  - `id`
  - `title`
  - `description`
  - `cta`
- `Plan`
  - `name`
  - `price`
  - `data`
  - `minutes`
  - `sms`

These are plain Kotlin data classes with no Android dependencies.

#### `data/repository`
- `CellularRepository` (interface)
  - Exposes:
    - `usageStats: StateFlow<UsageStats>`
    - `promos: StateFlow<List<Promo>>`
    - `plans: StateFlow<List<Plan>>`
  - Also provides mutation helpers:
    - `consumeData(mb: Int)`
    - `consumeMinutes(min: Int)`
    - `consumeSms(count: Int)`
- `FakeCellularRepository` (mock implementation)
  - Backed by `MutableStateFlow`
  - Starts with realistic mock values:
    - Usage: `2.3 GB of 5 GB`, `340 / 1000 min`, `120 / 500 SMS`
    - Renewal date: `Nov 1, 2025`
    - Balance example: `₹82.5`
    - Plans: Super 299 / Max 499 / Power 799
    - Promos: “Super Saver 499”, “Weekend Data Boost”, etc.
  - Calling `consumeData()`, `consumeMinutes()`, `consumeSms()` updates those flows,
    which simulates live usage changing over time

This satisfies “Mock data source (local JSON or static data)” and “Demonstrate dynamic updates”.

#### `ui/viewmodel`

**`DashboardViewModel`**
- Annotated with `@HiltViewModel`
- Injected with `CellularRepository`
- Uses `combine(repo.usageStats, repo.promos, repo.plans)` to build one `DashboardUiState`
  ```kotlin
  data class DashboardUiState(
      val usage: UsageStats,
      val promos: List<Promo>,
      val topPlans: List<Plan>
  )
