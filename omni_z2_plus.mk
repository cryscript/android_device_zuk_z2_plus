# Copyright (C) 2013-2016, The CyanogenMod Project
# Copyright (C) 2017, The LineageOS Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

#
# This file is the build configuration for a full Android
# build for grouper hardware. This cleanly combines a set of
# device-specific aspects (drivers) with a device-agnostic
# product configuration (apps).
#

# Sample: This is where we'd set a backup provider if we had one
# $(call inherit-product, device/sample/products/backup_overlay.mk)
$(call inherit-product, $(SRC_TARGET_DIR)/product/core_64_bit.mk)

# Get the prebuilt list of APNs
$(call inherit-product, vendor/omni/config/gsm.mk)

# Inherit from the common Open Source product configuration
$(call inherit-product, $(SRC_TARGET_DIR)/product/aosp_base_telephony.mk)

# Inherit from z2_plus device
$(call inherit-product, device/zuk/z2_plus/device.mk)

# must be before including omni part
TARGET_BOOTANIMATION_SIZE := 1080x608

# Inherit some common OMNI stuff.
$(call inherit-product, vendor/omni/config/common.mk)

# Discard inherited values and use our own instead.
PRODUCT_NAME := omni_z2_plus
PRODUCT_DEVICE := z2_plus
PRODUCT_MANUFACTURER := ZUK
PRODUCT_BRAND := ZUK
PRODUCT_MODEL := Z2 Plus

PRODUCT_AAPT_CONFIG := normal
PRODUCT_AAPT_PREF_CONFIG := xxhdpi

PRODUCT_GMS_CLIENTID_BASE := android-zuk

TARGET_VENDOR_PRODUCT_NAME := z2_plus
TARGET_VENDOR_DEVICE_NAME := z2_plus
PRODUCT_BUILD_PROP_OVERRIDES += TARGET_DEVICE=z2_plus PRODUCT_NAME=z2_plus

PRODUCT_BUILD_PROP_OVERRIDES += \
    PRODUCT_DEFAULT_LOCALE="ru-RU"

TARGET_VENDOR := zuk