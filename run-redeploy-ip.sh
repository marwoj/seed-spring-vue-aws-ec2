#!/usr/bin/env bash

ansible-playbook playbook-push.yml --extra-vars "seed_env=env-ip version_tag=1.0.0"
ansible-playbook playbook-run.yml --private-key ~/workspace/secret/seed.pem --extra-vars "seed_env=env-ip seed_hosts=ec2-seed db_setup=false"
