#!/bin/sh

terraform init -backend-config="key=$TF_STATE_KEY" -backend-config="bucket=$TF_STATE_BUCKET" -backend-config="region=$TF_VAR_region"
