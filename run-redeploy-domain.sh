#!/bin/bash

ansible-playbook playbook-push.yml --extra-vars "seed_env=env-domain version_tag=1.0.0"
ansible-playbook playbook-run.yml --private-key ~/workspace/secret/seed.pem --extra-vars "seed_env=env-domain seed_hosts=ec2-seed db_setup=false"
